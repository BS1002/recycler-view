package com.mahfuznow.recyclerview.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.math.MathUtils
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mahfuznow.recyclerview.R
import com.mahfuznow.recyclerview.adapter.TagListAdapter
import com.mahfuznow.recyclerview.model.Tag
import com.mahfuznow.recyclerview.utils.utils
import java.math.MathContext

class TagActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tag)

        val tags: List<Tag> = getDummyTagList()

        val adapter = TagListAdapter(this, tags)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)

        val layoutManager = GridLayoutManager(this, 3) // spanCount represents number of columns
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                //position -> current item position -> (0 - items.size)
                //return if (position % 5 == 0) 2 else 1 // each 5th item will take up the width of 2 cells
                return if (position % 4 == 0) 3 else 1 // each 4th item will take up the width of 4 cells
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