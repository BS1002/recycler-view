package com.mahfuznow.recyclerview.utils

import android.content.Context
import android.util.TypedValue


class Utils {

    companion object {
        fun gcd(x: Int, y: Int): Int {
            var a = x
            var b = y
            while (b != 0) {
                val t = a
                a = b
                b = t % b
            }
            return a
        }

        fun lcm(x: Int, y: Int): Int {
            return (x * y) / gcd(x, y)
        }

        fun pxFromDp(context: Context, dp: Int): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp.toFloat(),
                context.resources.displayMetrics
            ).toInt()
        }
    }


}