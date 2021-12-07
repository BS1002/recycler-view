package com.mahfuznow.recyclerview.manager

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.mahfuznow.recyclerview.utils.Utils

class PatternedGridLayoutManager(context: Context, x: Int, y: Int) : GridLayoutManager(
    context,
    Utils.lcm(x, y)
) {
    /*
        X and Y are representing a pattern, 1st row will have X items and 2nd row will have Y items. Only change these two values to generate new pattern
        Suppose, x = 2, y = 3
        ----------------------------
        000111
        223344
        555666
        778899
        ----------------------------
    */
    init {
        val lcm = Utils.lcm(x, y)
        val d = x + y
        super.setSpanSizeLookup(
            object : SpanSizeLookup() {
                //returns how much width(number of cells) each list items will take
                override fun getSpanSize(position: Int): Int {
                    val remainder = position % d
                    for (i in 0 until x)
                        if (remainder == i) return lcm / x //return spanSize = 3 for 0,1,5,6,11,12... positions
                    return lcm / y //return spanSize = 2 for rest of the positions
                }
            }
        )
    }
}