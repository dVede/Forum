package com.example.forum.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.forum.model.Message
import com.example.forum.R
import java.text.SimpleDateFormat
import java.util.*

class MessageAdapter : ListAdapter<Message, MessageAdapter.MyViewHolder>(MessageDiffCallback) {
    class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var nickname: TextView = itemView.findViewById(R.id.nickname)
        private var msg: TextView = itemView.findViewById(R.id.message_text)
        private var date: TextView = itemView.findViewById(R.id.date)
        private val output = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        private val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.ENGLISH)

        fun bind(message: Message) {
            nickname.text = message.name
            msg.text = message.msg
            date.text = output.format(input.parse(message.time)!!)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.message_item, parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    object MessageDiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }
}