package com.mahfuznow.recyclerview.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mahfuznow.recyclerview.R
import com.mahfuznow.recyclerview.adapter.TagListAdapter
import com.mahfuznow.recyclerview.model.Tag
import com.mahfuznow.recyclerview.utils.utils

class TagActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tag)

        val tags: List<Tag> = getDummyTagList()

        val adapter = TagListAdapter(this, tags)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)

        // X and Y are representing a pattern, 1st row will have X items and 2nd row will have Y items. Only change these two values to generate new pattern
        val x = 2
        val y = 3
        val lcm = utils.lcm(x,y)
        val d = x+y

        /*
        000111
        223344
        555666
        778899
        */

        val layoutManager = GridLayoutManager(this, lcm) // spanCount represents number of columns
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            //returns how much width(number of cells) each list items will take
            override fun getSpanSize(position: Int): Int {
                val remainder = position % d
                for(i in 0 until x)
                    if(remainder==i) return lcm/x //return spanSize = 3 for 0,1,5,6,11,12... positions
                return lcm/y //return spanSize = 2 for rest of the positions
            }
        }
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }


    private fun getDummyTagList(): List<Tag> {
        return arrayListOf(
            Tag("Android"),
            Tag("Java"),
            Tag("Kotlin"),
            Tag("C++"),
            Tag("Python"),
            Tag("HTML"),
            Tag("CSS"),
            Tag("JS"),
            Tag("PHP"),
            Tag("C#"),
            Tag("Flutter"),
            Tag("Unity 3D"),
            Tag("Unreal Engine"),
            Tag("3D"),
        )

    }
}