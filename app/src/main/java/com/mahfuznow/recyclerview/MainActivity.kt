package com.mahfuznow.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mahfuznow.recyclerview.adapter.CatAndFoodListAdapterDelegate
import com.mahfuznow.recyclerview.model.Cat
import com.mahfuznow.recyclerview.model.Food
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val catList = getDummyCatList()
        val foodList = getDummyFoodList()

        val mergedList: ArrayList<Any> = mergeList(catList,foodList)
        mergedList.shuffle()

        val adapter = CatAndFoodListAdapterDelegate(this)
        //items is a field defined in super class of the adapter
        adapter.items = mergedList

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun getDummyCatList(): ArrayList<Cat> {
        val catList : ArrayList<Cat> = ArrayList()
        val countries: ArrayList<String> = ArrayList()
        for (locale in Locale.getAvailableLocales())
            if(locale.displayCountry!="")
                countries.add(locale.displayCountry)
        for(i in 1..20) {
            val age = (1..10).random()
            val origin = countries.random().toString()
            val cat = Cat("Cat Name $i",age, origin)
            catList.add(cat)
        }
        return catList
    }

    private fun getDummyFoodList(): ArrayList<Food> {
        val foodList : ArrayList<Food> = ArrayList()
        for(i in 1..10) {
            val price = (200..1000).random().toDouble()
            val food = Food("Food Name $i",price, "Manufacturer")
            foodList.add(food)
        }
        return foodList
    }

    private fun mergeList(list1: ArrayList<Cat>, list2: ArrayList<Food>): ArrayList<Any> {
        val mergedList:ArrayList<Any> = ArrayList()
        mergedList.addAll(list1)
        mergedList.addAll(list2)
        return  mergedList
    }
}