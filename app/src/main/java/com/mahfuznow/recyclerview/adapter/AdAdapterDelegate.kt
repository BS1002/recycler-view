package com.mahfuznow.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mahfuznow.recyclerview.R
import com.mahfuznow.recyclerview.model.Ad

class AdAdapterDelegate(private val context: Context, val viewType: Int) {

    fun isForViewType(items: List<Any>, position: Int) = items[position] is Ad

    fun onCreateViewHolder(parent: ViewGroup) =
        AdViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ad, parent, false))

    fun onBindViewHolder(ads: List<Any>, position: Int, holder: RecyclerView.ViewHolder) {
        val ad = ads[position]
        ad as Ad
        holder as AdViewHolder
    }

    class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
    }

}