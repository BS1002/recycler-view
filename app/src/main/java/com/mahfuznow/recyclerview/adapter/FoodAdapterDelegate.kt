package com.mahfuznow.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.mahfuznow.recyclerview.R
import com.mahfuznow.recyclerview.model.Food

class FoodAdapterDelegate(private val context: Context) :
    AdapterDelegate<ArrayList<Any>>() {

    public override fun isForViewType(items: ArrayList<Any>, position: Int) = items[position] is Food

    public override fun onCreateViewHolder(parent: ViewGroup) =
        FoodViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false))

    public override fun onBindViewHolder(
        items: ArrayList<Any>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        val food = items[position]
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