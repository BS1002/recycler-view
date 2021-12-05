package com.mahfuznow.recyclerview.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.mahfuznow.recyclerview.R
import com.mahfuznow.recyclerview.adapter.TagListAdapter
import com.mahfuznow.recyclerview.model.Tag

class TagActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tag)

        val tags: List<Tag> = getDummyTagList()

        val adapter = TagListAdapter(this, tags)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        //Layout manager can either declared in XML or Programmatically
        //recyclerView.layoutManager = GridLayoutManager(this, 3)
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