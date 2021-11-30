package com.mahfuznow.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mahfuznow.recyclerview.R
import com.mahfuznow.recyclerview.model.Cat

class CatAdapterDelegate(private val context: Context, val viewType: Int) {

    fun isForViewType(items: List<Any>, position: Int) = items[position] is Cat

    fun onCreateViewHolder(parent: ViewGroup) =
        CatViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cat, parent, false))

    fun onBindViewHolder(cats: List<Any>, position: Int, holder: RecyclerView.ViewHolder) {
        val cat = cats[position]
        cat as Cat
        holder as CatViewHolder
        holder.tvName.text = cat.name
        holder.tvAge.text = cat.age.toString()
        holder.tvOrigin.text = cat.origin

        holder.itemView.setOnClickListener {
            Toast.makeText(context, "You have clicked ${cat.name}", Toast.LENGTH_SHORT).show()
        }
    }

    class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvAge: TextView = itemView.findViewById(R.id.tvAge)
        val tvOrigin: TextView = itemView.findViewById(R.id.tvOrigin)
    }

}