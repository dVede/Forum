package com.example.forum.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.forum.R

class ThreadsAdapter(private val onItemClicked: (String) -> Unit)
    : ListAdapter<String, ThreadsAdapter.MyViewHolder>(ThreadsDiffCallback){

    class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var thread: TextView = itemView.findViewById(R.id.text_field)

        fun bind(threadName: String) {
            thread.text = threadName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.textview_card, parent, false))



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener { onItemClicked(item)}
    }

    object ThreadsDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}
