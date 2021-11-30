package com.mahfuznow.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mahfuznow.recyclerview.R
import com.mahfuznow.recyclerview.model.Food

class FoodAdapterDelegate(private val context: Context, val viewType: Int) {

    fun isForViewType(items: List<Any>, position: Int) = items[position] is Food

    fun onCreateViewHolder(parent: ViewGroup) =
        FoodViewHolder(LayoutInflater.from(context).inflate(R.layout.item_food, parent, false))

    fun onBindViewHolder(foods: List<Any>, position: Int, holder: RecyclerView.ViewHolder) {
        val food = foods[position]
        food as Food
        holder as FoodViewHolder
        holder.tvName.text = food.name
        holder.tvPrice.text = food.price.toString()
        holder.tvManufacturer.text = food.manufacturer

        holder.itemView.setOnClickListener {
            Toast.makeText(context, "You have clicked ${food.name}", Toast.LENGTH_SHORT).show()
        }
    }

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val tvManufacturer: TextView = itemView.findViewById(R.id.tvManufacturer)
    }

}