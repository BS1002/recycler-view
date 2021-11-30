package com.mahfuznow.recyclerview.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CatAndFoodListAdapterDelegate(
    private val context: Context,
    private val items: ArrayList<Any>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_CAT = 0
        private const val VIEW_TYPE_FOOD = 1
        private const val VIEW_TYPE_AD = 2
    }

    private val catAdapterDelegate = CatAdapterDelegate(context, VIEW_TYPE_CAT)
    private val foodAdapterDelegate = FoodAdapterDelegate(context, VIEW_TYPE_FOOD)
    private val adAdapterDelegate = AdAdapterDelegate(context, VIEW_TYPE_AD)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            catAdapterDelegate.viewType -> catAdapterDelegate.onCreateViewHolder(parent)
            foodAdapterDelegate.viewType -> foodAdapterDelegate.onCreateViewHolder(parent)
            else -> adAdapterDelegate.onCreateViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            catAdapterDelegate.viewType -> catAdapterDelegate.onBindViewHolder(
                items,
                position,
                holder
            )
            foodAdapterDelegate.viewType -> foodAdapterDelegate.onBindViewHolder(
                items,
                position,
                holder
            )
            adAdapterDelegate.viewType -> adAdapterDelegate.onBindViewHolder(
                items,
                position,
                holder
            )
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return when {
            catAdapterDelegate.isForViewType(items, position) -> catAdapterDelegate.viewType
            foodAdapterDelegate.isForViewType(items, position) -> foodAdapterDelegate.viewType
            else -> adAdapterDelegate.viewType
        }
    }
}