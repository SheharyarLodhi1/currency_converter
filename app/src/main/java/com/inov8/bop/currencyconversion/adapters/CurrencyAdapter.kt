package com.inov8.bop.currencyconversion.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.inov8.bop.currencyconversion.R
import com.inov8.bop.currencyconversion.data.model.Quotes
import javax.xml.transform.dom.DOMLocator

class CurrencyAdapter(var context:Context) : RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {
    //var dataList = emptyList<Quotes>()
    var dataList = HashMap<String, Double>()

    internal fun setDataList(dataList: HashMap<String, Double>) {
        this.dataList = dataList
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var currency: TextView

        init {
            title = itemView.findViewById(R.id.title)
            currency = itemView.findViewById(R.id.currency)
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyAdapter.ViewHolder {

        // Inflate the custom layout
        var view = LayoutInflater.from(parent.context).inflate(R.layout.grid_layout, parent, false)
        return ViewHolder(view)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(holder: CurrencyAdapter.ViewHolder, position: Int) {

        // Get the data model based on position
        var data = dataList
        var list : ArrayList<String> = ArrayList()
        var valuesList : ArrayList<String> = ArrayList()

        for (key in dataList.keys) {
            var keys = dataList.keys.iterator().next()
            var keyzz = dataList.get(key)
            list.add(key)
            valuesList.add(dataList.get(key).toString())
        }
        holder.title.text = list.get(position)
        holder.currency.text = valuesList.get(position)
    }

    //  total count of items in the list
    override fun getItemCount() = dataList.size
}