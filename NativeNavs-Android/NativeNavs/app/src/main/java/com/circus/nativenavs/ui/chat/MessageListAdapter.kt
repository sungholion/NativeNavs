package com.circus.nativenavs.ui.chat

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.circus.nativenavs.R
import com.circus.nativenavs.data.MessageDto
import com.circus.nativenavs.databinding.ItemMessageBinding
import com.circus.nativenavs.util.SharedPref

class MessageListAdapter :
    ListAdapter<MessageDto, MessageListAdapter.MessageViewHolder>(MessageComparator) {

    companion object MessageComparator : DiffUtil.ItemCallback<MessageDto>() {
        override fun areItemsTheSame(oldItem: MessageDto, newItem: MessageDto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MessageDto, newItem: MessageDto): Boolean {
            return oldItem == newItem
        }

    }

    inner class MessageViewHolder(val binding: ItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: MessageDto) {
            binding.message = message

            if (message.senderId == SharedPref.userId) {
                binding.chatMyMessageCl.visibility = View.VISIBLE
                binding.chatSenderMessageCl.visibility = View.GONE
            } else {
                binding.chatMyMessageCl.visibility = View.GONE
                binding.chatSenderMessageCl.visibility = View.VISIBLE

                binding.chatSenderMessageTv.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            }

//            binding.root.setOnClickListener {
//                itemClickListener.onItemClicked(chat.chatId)
//            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = DataBindingUtil.inflate<ItemMessageBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_message,
            parent,
            false
        )

        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface ChatItemClickListener {
        fun onItemClicked(chatId: Int)
    }

    private lateinit var itemClickListener: ChatItemClickListener

    fun setItemClickListener(itemClickListener: ChatItemClickListener) {
        this.itemClickListener = itemClickListener
    }

}