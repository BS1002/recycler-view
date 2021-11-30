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


private const val VIEW_TYPE_CAT = 0
private const val VIEW_TYPE_FOOD = 1
private const val VIEW_TYPE_AD = 2

class CatAndFoodListAdapter(private val context: Context, private val items: ArrayList<Any>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_CAT)
            return CatViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_cat, parent, false)
            )
        else if (viewType == VIEW_TYPE_FOOD)
            return FoodViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
            )
        return AdViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_ad, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        val viewType = getItemViewType(position)
        if (viewType == VIEW_TYPE_CAT) {
            item as Cat
            holder as CatViewHolder

            holder.tvName.text = item.name
            holder.tvAge.text = item.age.toString()
            holder.tvOrigin.text = item.origin

            holder.itemView.setOnClickListener {
                Toast.makeText(context, "You have clicked ${item.name}", Toast.LENGTH_SHORT).show()
            }

        } else if (viewType == VIEW_TYPE_FOOD) {
            item as Food
            holder as FoodViewHolder

            holder.tvName.text = item.name
            holder.tvPrice.text = item.price.toString()
            holder.tvManufacturer.text = item.manufacturer

            holder.itemView.setOnClickListener {
                Toast.makeText(context, "You have clicked ${item.name}", Toast.LENGTH_SHORT).show()
            }
        } else {
            TODO("Advertisement")
            //Advertisement
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    //this override is required to find out the viewType in onCreateViewHolder method
    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        if (item is Cat)
            return VIEW_TYPE_CAT
        else if (item is Food)
            return VIEW_TYPE_FOOD
        return VIEW_TYPE_AD
    }

    class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvAge: TextView = itemView.findViewById(R.id.tvAge)
        val tvOrigin: TextView = itemView.findViewById(R.id.tvOrigin)
    }

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val tvManufacturer: TextView = itemView.findViewById(R.id.tvManufacturer)
    }

    class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Advertisement
    }

}