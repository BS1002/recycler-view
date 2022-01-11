package com.mahfuznow.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.mahfuznow.recyclerview.R
import com.mahfuznow.recyclerview.model.Ad

class AdAdapterDelegate: AdapterDelegate<ArrayList<Any>>() {

    lateinit var context: Context

    override fun isForViewType(items: ArrayList<Any>, position: Int) = items[position] is Ad

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_ad, parent, false)
        return AdViewHolder(view)
    }

    override fun onBindViewHolder(items: ArrayList<Any>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        holder as AdViewHolder
        val item = items[position] as Ad
        holder.onBind(context,item)
    }

    class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
        val title: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.description)

        fun onBind(context: Context,item: Ad) {
            title.text = item.title
            description.text = item.description
        }
    }

}