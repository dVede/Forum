package com.example.forum.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.forum.R

class ActiveUsersAdapter : ListAdapter<String, ActiveUsersAdapter.MyViewHolder>(UsersDiffCallback) {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private var usernameField: TextView = itemView.findViewById(R.id.text_field)

        fun bind(username: String) {
            usernameField.text = username
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.textview_card, parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) =
        holder.bind(getItem(position))

    object UsersDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

}