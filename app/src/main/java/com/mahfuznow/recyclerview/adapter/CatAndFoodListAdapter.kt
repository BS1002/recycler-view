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
import com.mahfuznow.recyclerview.model.Food

class CatAndFoodListAdapter(private val context: Context, private val items: ArrayList<Any>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_CAT = 0
        private const val VIEW_TYPE_FOOD = 1
        private const val VIEW_TYPE_AD = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_CAT -> CatViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_cat, parent, false)
            )
            VIEW_TYPE_FOOD ->
                FoodViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
                )
            else ->
                AdViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_ad, parent, false)
                )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (getItemViewType(position)) {
            VIEW_TYPE_CAT -> {
                item as Cat
                holder as CatViewHolder
                holder.onBind(context, item)
            }
            VIEW_TYPE_FOOD -> {
                item as Food
                holder as FoodViewHolder
                holder.onBind(context, item)
            }
            else -> {
                TODO("Advertisement")
                //Advertisement
            }
        }
    }

    override fun getItemCount() = items.size

    //this override is required to find out the viewType in onCreateViewHolder method
    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Cat -> VIEW_TYPE_CAT
            is Food -> VIEW_TYPE_FOOD
            else -> VIEW_TYPE_AD
        }
    }

    class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvAge: TextView = itemView.findViewById(R.id.tvAge)
        private val tvOrigin: TextView = itemView.findViewById(R.id.tvOrigin)

        fun onBind(context: Context, item: Cat) {
            tvName.text = item.name
            tvAge.text = item.age.toString()
            tvOrigin.text = item.origin
            itemView.setOnClickListener {
                Toast.makeText(context, "You have clicked ${item.name}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        private val tvManufacturer: TextView = itemView.findViewById(R.id.tvManufacturer)

        fun onBind(context: Context, item: Food) {
            tvName.text = item.name
            tvPrice.text = item.price.toString()
            tvManufacturer.text = item.manufacturer
            itemView.setOnClickListener {
                Toast.makeText(context, "You have clicked ${item.name}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Advertisement
    }

}