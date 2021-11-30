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

class AdAdapterDelegate(private val context: Context) :
    AdapterDelegate<ArrayList<Any>>() {

    override fun isForViewType(items: ArrayList<Any>, position: Int) = items[position] is Ad

    override fun onCreateViewHolder(parent: ViewGroup) =
        AdViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_ad, parent, false))

    override fun onBindViewHolder(
        items: ArrayList<Any>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        val ad = items[position]
        ad as Ad
        holder as AdViewHolder
    }

    class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
    }

}