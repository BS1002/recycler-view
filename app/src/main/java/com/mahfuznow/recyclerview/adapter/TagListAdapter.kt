package com.mahfuznow.recyclerview.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mahfuznow.recyclerview.R
import com.mahfuznow.recyclerview.model.Tag

class TagListAdapter(val context: Context, private val items: List<Tag>) :
    RecyclerView.Adapter<TagListAdapter.TagViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tag, parent, false)
        return TagViewHolder(view)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.onBind(context, items[position])
    }

    override fun getItemCount(): Int = items.size


    class TagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val linearLayout: LinearLayout = itemView.findViewById(R.id.linearLayout)
        val textView: TextView = itemView.findViewById(R.id.textView)

        fun onBind(context: Context, tag: Tag) {

            textView.text = tag.name

            val textColor = textView.currentTextColor
            val textColorSelected = Color.WHITE
            val primaryColor = ContextCompat.getColor(context, R.color.design_default_color_primary)

            var isSelected = false
            linearLayout.setOnClickListener {
                isSelected = !isSelected
                if (isSelected) {
                    linearLayout.setBackgroundColor(primaryColor)
                    textView.setTextColor(textColorSelected)
                } else {
                    linearLayout.background = null
                    textView.setTextColor(textColor)
                }
            }

        }
    }
}
