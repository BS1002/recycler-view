package com.mahfuznow.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mahfuznow.recyclerview.adapter.CatListAdapter
import com.mahfuznow.recyclerview.model.Cat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val catList = getDummyCatList()
        val adapter = CatListAdapter(this,catList)

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
}