package com.mahfuznow.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mahfuznow.recyclerview.R
import com.mahfuznow.recyclerview.model.Cat

class CatListAdapter(private val context: Context, private val cats: ArrayList<Cat>) :
    RecyclerView.Adapter<CatListAdapter.CatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cat,parent,false)
        return CatViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val cat = cats[position]
        holder.tvName.text = cat.name
        holder.tvAge.text = cat.age.toString()
        holder.tvOrigin.text = cat.origin

        holder.itemView.setOnClickListener {
            Toast.makeText(context,"You have clicked ${cat.name}",Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return cats.size
    }

    class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvAge: TextView = itemView.findViewById(R.id.tvAge)
        val tvOrigin: TextView = itemView.findViewById(R.id.tvOrigin)
    }

}