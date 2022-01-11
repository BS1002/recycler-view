package com.mahfuznow.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.mahfuznow.recyclerview.R
import com.mahfuznow.recyclerview.model.Video


class VideoAdapterDelegate : AdapterDelegate<ArrayList<Any>>() {

    lateinit var context: Context

    override fun isForViewType(items: ArrayList<Any>, position: Int): Boolean {
        return items[position] is Video
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(items: ArrayList<Any>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        holder as VideoViewHolder
        val item = items[position] as Video
        holder.onBind(context,item)
    }

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val description: TextView = itemView.findViewById(R.id.description)
        val thumbnail: ImageView = itemView.findViewById(R.id.thumbnail)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        val volumeControl: ImageView = itemView.findViewById(R.id.volume_control)
        val frameLayout: FrameLayout = itemView.findViewById(R.id.frame_layout)

        fun onBind(context: Context, item: Video) {
            itemView.tag = this // This line is very important to find this viewHolder inside the Custom RecyclerView
            title.text = item.title
            description.text = item.description
            Glide.with(context)
                .load(item.thumbnail)
                .fitCenter()
                .into(thumbnail)
        }
    }
}