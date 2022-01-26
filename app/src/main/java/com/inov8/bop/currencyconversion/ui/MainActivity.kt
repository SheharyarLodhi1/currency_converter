package com.inov8.bop.currencyconversion.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.inov8.bop.currencyconversion.adapters.CurrencyAdapter
import com.inov8.bop.currencyconversion.base.ActivityBase
import com.inov8.bop.currencyconversion.data.model.Quotes
import com.inov8.bop.currencyconversion.databinding.ActivityMainBinding
import com.inov8.bop.currencyconversion.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect
@AndroidEntryPoint
class MainActivity : ActivityBase() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var  currencyAdapter: CurrencyAdapter
    private var dataList = mutableListOf<Quotes>()

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.spFromCurrency?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.convert( binding.etFrom.text.toString(),
                    applicationContext,
                    binding.spFromCurrency.selectedItem.toString(),
                )
            }
            
        }


        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collect { event ->
                when(event) {
                    is MainViewModel.CurrencyEvent.Success -> {
                        binding.progressBar.isVisible = false
                        //binding.tvResult.setTextColor(Color.BLACK)
                        //binding.tvResult.text = event.resultText

                        //show here recyclerview updated values
                        var readSharedPrefVal = readFromSP(applicationContext)
                        binding.recyclerView.layoutManager = GridLayoutManager(applicationContext,2)
                        currencyAdapter = CurrencyAdapter(applicationContext)
                        recyclerView.adapter = currencyAdapter
                        currencyAdapter.setDataList(readSharedPrefVal)
                    }
                    is MainViewModel.CurrencyEvent.Failure -> {
                        binding.progressBar.isVisible = false
                       /* binding.tvResult.setTextColor(Color.RED)
                        binding.tvResult.text = event.errorText*/
                    }
                    is MainViewModel.CurrencyEvent.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun readFromSP(context: Context): HashMap<String, Double> {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("HashMap", MODE_PRIVATE)
        val defValue = Gson().toJson(HashMap<String, List<String>>())
        val json = sharedPreferences.getString("map", defValue)
        val token: TypeToken<HashMap<String, Double>> = object :
            TypeToken<HashMap<String, Double>>() {}
        return Gson().fromJson(json, token.type)
    }
}