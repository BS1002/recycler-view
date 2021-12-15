package com.mahfuznow.recyclerview.adapter

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mahfuznow.recyclerview.R
import com.mahfuznow.recyclerview.model.Hexagon
import androidx.recyclerview.widget.GridLayoutManager
import com.mahfuznow.recyclerview.utils.Utils


class HexagonListAdapter(
    val context: Context,
    private val items: List<Hexagon>,
    private val x: Int,
    private val y: Int
) :
    RecyclerView.Adapter<HexagonListAdapter.HexagonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HexagonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hexagon, parent, false)
        return HexagonViewHolder(view)
    }

    override fun onBindViewHolder(holder: HexagonViewHolder, position: Int) {
        holder.onBind(context, items, position, x, y)
    }

    override fun getItemCount(): Int = items.size


    class HexagonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val linearLayout: LinearLayout = itemView.findViewById(R.id.linearLayout)
        val textView: TextView = itemView.findViewById(R.id.textView)

        fun onBind(context: Context, items: List<Hexagon>, position: Int, x: Int, y: Int) {

            val hexagon = items[position]
            textView.text = hexagon.name

            val textColor = textView.currentTextColor
            val textColorSelected = Color.WHITE

            var isSelected = false
            textView.setOnClickListener {
                isSelected = !isSelected
                if (isSelected) {
                    textView.background =
                        ContextCompat.getDrawable(context, R.drawable.hexagon_filled)
                    textView.setTextColor(textColorSelected)
                } else {
                    textView.background =
                        ContextCompat.getDrawable(context, R.drawable.hexagon_outline)
                    textView.setTextColor(textColor)
                }
            }

            //adjusting the hexagon spacing & gravity
            // this will only work if x=4 & y=3
            val param = linearLayout.layoutParams as GridLayoutManager.LayoutParams
            val negativeMargin = Utils.pxFromDp(context, -18)
            // pulling up every rows except the first row
            if (position >= x) param.topMargin = negativeMargin
            val d = x + y
            val rem = position % d
            if (rem >= x) {
                // Dealing with the 2nd, 4th, 6th .... 2nth rows
                val index = rem - x // index of the hexagon in the current row
                when (index) {
                    0-> {
                        linearLayout.gravity = Gravity.END
                        param.rightMargin = negativeMargin
                    }
                    1 -> {
                        linearLayout.gravity = Gravity.CENTER
                    }
                    2 -> {
                        linearLayout.gravity = Gravity.START
                        param.leftMargin = negativeMargin
                    }
                }
            }

        }
    }
}
