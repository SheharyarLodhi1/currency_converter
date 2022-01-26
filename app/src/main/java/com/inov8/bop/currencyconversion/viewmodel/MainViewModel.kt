package com.inov8.bop.currencyconversion.viewmodel

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inov8.bop.currencyconversion.data.model.Quotes
import com.inov8.bop.currencyconversion.utils.DispatcherProvider
import com.inov8.bop.currencyconversion.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.content.SharedPreferences

import android.content.Context.MODE_PRIVATE

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainViewModel @ViewModelInject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    sealed class CurrencyEvent {
        class Success(val resultText: String) : CurrencyEvent()
        class Failure(val errorText: String) : CurrencyEvent()
        object Loading : CurrencyEvent()
        object Empty : CurrencyEvent()
    }

    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val conversion: StateFlow<CurrencyEvent> = _conversion
    var arrayList: ArrayList<Double> = ArrayList()


    fun convert(
        amountStr: String,
        context: Context,
        fromCurrency: String
        /*toCurrency: String*/

    ) {
        val fromAmount = amountStr.toFloatOrNull()
        if (fromAmount == null) {
            _conversion.value = CurrencyEvent.Failure("Not a valid amount")
            return
        }

        viewModelScope.launch(dispatchers.io) {
            _conversion.value = CurrencyEvent.Loading
            when (val ratesResponse = repository.getRates(fromCurrency)) {
                is Resource.Error -> _conversion.value =
                    CurrencyEvent.Failure(ratesResponse.message!!)
                is Resource.Success -> {
                    val rates = ratesResponse.data!!.quotes
                    var hashMap: HashMap<String, Double> = HashMap<String, Double>()
                    hashMap.put("USDALL", rates.USDALL!!)
                    hashMap.put("USDAED", rates.USDAED!!)
                    hashMap.put("USDAFN", rates.USDAFN!!)
                    hashMap.put("USDAMD", rates.USDAMD!!)
                    hashMap.put("USDANG", rates.USDANG!!)
                    hashMap.put("USDAOA", rates.USDAOA!!)
                    hashMap.put("USDARS", rates.USDARS!!)
                    hashMap.put("USDAUD", rates.USDAUD!!)
                    hashMap.put("USDAWG", rates.USDAWG!!)
                    hashMap.put("USDAZN", rates.USDAZN!!)
                    hashMap.put("USDBAM", rates.USDBAM!!)
                    hashMap.put("USDBBD", rates.USDBBD!!)
                    hashMap.put("USDBDT", rates.USDBDT!!)
                    hashMap.put("USDBGN", rates.USDBGN!!)
                    hashMap.put("USDBHD", rates.USDBHD!!)
                    hashMap.put("USDBIF", rates.USDBIF!!)
                    hashMap.put("USDBMD", rates.USDBMD!!)
                    hashMap.put("USDBND", rates.USDBND!!)
                    hashMap.put("USDBOB", rates.USDBOB!!)
                    hashMap.put("USDBRL", rates.USDBRL!!)
                    hashMap.put("USDBSD", rates.USDBSD!!)
                    hashMap.put("USDBTC", rates.USDBTC!!)
                    hashMap.put("USDBTN", rates.USDBTN!!)
                    hashMap.put("USDBWP", rates.USDBWP!!)
                    hashMap.put("USDBYN", rates.USDBYN!!)
                    hashMap.put("USDBYR", rates.USDBYR!!)
                    hashMap.put("USDBZD", rates.USDBZD!!)
                    hashMap.put("USDCAD", rates.USDCAD!!)
                    hashMap.put("USDCDF", rates.USDCDF!!)
                    hashMap.put("USDCHF", rates.USDCHF!!)
                    hashMap.put("USDCLF", rates.USDCLF!!)
                    hashMap.put("USDCLP", rates.USDCLP!!)
                    //remove previous shared pref values and add fresh values to pref..
                    removeValuesFromPreference(context)
                    var readSharedPrefVal = readFromSP(context)
                    insertToSP(context, hashMap)
                    /*arrayList.add(rates.USDCNY!!)
                    arrayList.add(rates.USDCOP!!)
                    arrayList.add(rates.USDCRC!!)
                    arrayList.add(rates.USDCUC!!)
                    arrayList.add(rates.USDCVE!!)
                    arrayList.add(rates.USDCZK!!)
                    arrayList.add(rates.USDDJF!!)
                    arrayList.add(rates.USDDKK!!)
                    arrayList.add(rates.USDDOP!!)
                    arrayList.add(rates.USDDZD!!)
                    arrayList.add(rates.USDEGP!!)
                    arrayList.add(rates.USDERN!!)
                    arrayList.add(rates.USDETB!!)
                    arrayList.add(rates.USDEUR!!)
                    arrayList.add(rates.USDFJD!!)
                    arrayList.add(rates.USDFKP!!)
                    arrayList.add(rates.USDGBP!!)
                    arrayList.add(rates.USDGEL!!)
                    arrayList.add(rates.USDGGP!!)
                    arrayList.add(rates.USDGHS!!)
                    arrayList.add(rates.USDGIP!!)
                    arrayList.add(rates.USDGMD!!)
                    arrayList.add(rates.USDGNF!!)
                    arrayList.add(rates.USDGTQ!!)
                    arrayList.add(rates.USDGYD!!)
                    arrayList.add(rates.USDHKD!!)
                    arrayList.add(rates.USDHNL!!)
                    arrayList.add(rates.USDHRK!!)
                    arrayList.add(rates.USDHTG!!)
                    arrayList.add(rates.USDHUF!!)
                    arrayList.add(rates.USDIDR!!)
                    arrayList.add(rates.USDILS!!)
                    arrayList.add(rates.USDIMP!!)
                    arrayList.add(rates.USDINR!!)
                    arrayList.add(rates.USDIQD!!)
                    arrayList.add(rates.USDIRR!!)
                    arrayList.add(rates.USDISK!!)
                    arrayList.add(rates.USDJEP!!)
                    arrayList.add(rates.USDJMD!!)
                    arrayList.add(rates.USDJOD!!)
                    arrayList.add(rates.USDJPY!!)
                    arrayList.add(rates.USDKES!!)
                    arrayList.add(rates.USDKGS!!)
                    arrayList.add(rates.USDKHR!!)
                    arrayList.add(rates.USDKMF!!)
                    arrayList.add(rates.USDKPW!!)
                    arrayList.add(rates.USDKRW!!)
                    arrayList.add(rates.USDKWD!!)
                    arrayList.add(rates.USDKYD!!)
                    arrayList.add(rates.USDKZT!!)
                    arrayList.add(rates.USDLAK!!)
                    arrayList.add(rates.USDLBP!!)
                    arrayList.add(rates.USDLKR!!)*/


                    //val rate = getRateForCurrency(/*toCurrency*/fromCurrency, rates)
                    if (rates/*rate*/ == null) {
                        _conversion.value = CurrencyEvent.Failure("Unexpected error")
                    } else {
                        //val convertedCurrency = round(fromAmount * rate * 100) / 100
                        //_conversion.value = CurrencyEvent.Success(
                        //"$fromAmount $fromCurrency = $convertedCurrency $fromCurrency/*toCurrency*/"
                        //)

                        // Show all rates in grid view
                        _conversion.value = CurrencyEvent.Success("SUccess")

                    }
                }
            }
        }
    }

    private fun removeValuesFromPreference(context: Context) {
        val preferences: SharedPreferences = context.getSharedPreferences("HashMap", 0)
        preferences.edit().clear().apply()
    }

    private fun getRateForCurrency(currency: String, rates: Quotes) = when (currency) {
        "CAD" -> rates.USDCAD
        "HKD" -> rates.USDHKD
        "ISK" -> rates.USDISK
        "EUR" -> rates.USDEUR
        "PHP" -> rates.USDPHP
        "DKK" -> rates.USDDKK
        "HUF" -> rates.USDHUF
        "CZK" -> rates.USDCZK
        "AUD" -> rates.USDAUD
        "RON" -> rates.USDRON
        "SEK" -> rates.USDSEK
        "IDR" -> rates.USDIDR
        "INR" -> rates.USDINR
        "BRL" -> rates.USDBRL
        "RUB" -> rates.USDRUB
        "HRK" -> rates.USDHRK
        "JPY" -> rates.USDJPY
        "THB" -> rates.USDTHB
        "CHF" -> rates.USDCHF
        "SGD" -> rates.USDSGD
        "PLN" -> rates.USDPLN
        "BGN" -> rates.USDBGN
        "CNY" -> rates.USDCNY
        "NOK" -> rates.USDNOK
        "NZD" -> rates.USDNZD
        "ZAR" -> rates.USDZAR
        "USD" -> rates.USD
        "MXN" -> rates.USDMXN
        "ILS" -> rates.USDILS
        "GBP" -> rates.USDGBP
        "KRW" -> rates.USDKRW
        "MYR" -> rates.USDMYR
        "PKR" -> rates.USDPKR
        else -> null
    }

    private fun insertToSP(context: Context, jsonMap: HashMap<String, Double>) {
        val jsonString = Gson().toJson(jsonMap)
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("HashMap", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("map", jsonString)
        editor.apply()
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