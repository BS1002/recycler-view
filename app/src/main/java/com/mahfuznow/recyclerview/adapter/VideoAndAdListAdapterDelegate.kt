package com.mahfuznow.recyclerview.adapter

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class VideoAndAdListAdapterDelegate : ListDelegationAdapter<ArrayList<Any>>() {
    init {
        // delegatesManager is a field defined in super class
        // ViewType integer is assigned internally by delegatesManager
        delegatesManager.addDelegate(VideoAdapterDelegate())
        delegatesManager.addDelegate(AdAdapterDelegate())
    }
}