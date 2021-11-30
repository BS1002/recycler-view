package com.mahfuznow.recyclerview.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager

class CatAndFoodListAdapterDelegate(
    private val context: Context,
    private val items: ArrayList<Any>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val adapterDelegatesManager = AdapterDelegatesManager<ArrayList<Any>>()

    init {
        adapterDelegatesManager.addDelegate(CatAdapterDelegate(context))
        adapterDelegatesManager.addDelegate(FoodAdapterDelegate(context))
        adapterDelegatesManager.addDelegate(AdAdapterDelegate(context))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return adapterDelegatesManager.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        adapterDelegatesManager.onBindViewHolder(items, position, holder)
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return adapterDelegatesManager.getItemViewType(items, position)
    }
}