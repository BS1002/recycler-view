package com.mahfuznow.recyclerview.adapter

import android.content.Context
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class CatAndFoodListAdapterDelegate(
    context: Context
) : ListDelegationAdapter<ArrayList<Any>>() {

    init {
        // delegatesManager is a field defined in super class
        // ViewType integer is assigned internally by delegatesManager
        delegatesManager.addDelegate(CatAdapterDelegate(context))
        delegatesManager.addDelegate(FoodAdapterDelegate(context))
        delegatesManager.addDelegate(AdAdapterDelegate())
    }
}