package com.mahfuznow.recyclerview.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.mahfuznow.recyclerview.R
import com.mahfuznow.recyclerview.adapter.HexagonListAdapter
import com.mahfuznow.recyclerview.manager.PatternedGridLayoutManager
import com.mahfuznow.recyclerview.model.Hexagon

class HexagonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hexagon)

        val hexagons: List<Hexagon> = getDummyHexagonList()

        val adapter = HexagonListAdapter(this, hexagons, 3, 2) //X and Y are representing a pattern, 1st row will have X items and 2nd row will have Y items.

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = PatternedGridLayoutManager(this,3,2) //X and Y are representing a pattern, 1st row will have X items and 2nd row will have Y items.
        recyclerView.adapter = adapter
    }


    private fun getDummyHexagonList(): List<Hexagon> {
        return arrayListOf(
            Hexagon("Apple"),
            Hexagon("Xiaomi"),
            Hexagon("Hp"),
            Hexagon("Lenovo"),
            Hexagon("Asus"),
            Hexagon("Acer"),
            Hexagon("Samsung"),
            Hexagon("Oppo"),
            Hexagon("Huawei"),
            Hexagon("Msi"),
            Hexagon("Dell"),
            Hexagon("Techno"),
            Hexagon("Gigabit"),
            Hexagon("Nokia"),
            Hexagon("Sony"),
            Hexagon("Motorola"),
            Hexagon("LG"),
            Hexagon("RealMe"),
            Hexagon("ZTE"),
            Hexagon("Google"),
            Hexagon("HTC"),
            Hexagon("OnePlus"),
        )

    }
}