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
import com.mahfuznow.recyclerview.R
import com.mahfuznow.recyclerview.model.Video

class VideoListAdapter(private val context: Context, val items: List<Video>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as VideoViewHolder
        holder.onBind(context, items, position)
    }

    override fun getItemCount() = items.size

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val description: TextView = itemView.findViewById(R.id.description)
        val thumbnail: ImageView = itemView.findViewById(R.id.thumbnail)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        val volumeControl: ImageView = itemView.findViewById(R.id.volume_control)
        val frameLayout: FrameLayout = itemView.findViewById(R.id.frame_layout)

        fun onBind(context: Context, items: List<Video>, position: Int) {
            itemView.tag = this // This line is very important to find this viewHolder inside the Custom RecyclerView
            val media = items[position]
            title.text = media.title
            description.text = media.description
            Glide.with(context)
                .load(media.thumbnail)
                .fitCenter()
                .into(thumbnail)
        }
    }
}