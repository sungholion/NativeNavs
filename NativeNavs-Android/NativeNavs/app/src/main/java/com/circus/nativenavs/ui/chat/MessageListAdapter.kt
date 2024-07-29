package com.circus.nativenavs.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.circus.nativenavs.R
import com.circus.nativenavs.data.ChatListDto
import com.circus.nativenavs.data.MessageDto
import com.circus.nativenavs.databinding.ItemChatroomBinding

class MessageListAdapter :
    ListAdapter<MessageDto, MessageListAdapter.MessageViewHolder>(MessageComparator) {

    companion object MessageComparator : DiffUtil.ItemCallback<MessageDto>() {
        override fun areItemsTheSame(oldItem: MessageDto, newItem: MessageDto): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: MessageDto, newItem: MessageDto): Boolean {
            return oldItem == newItem
        }

    }

    inner class MessageViewHolder(val binding: ItemChatroomBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: MessageDto) {
//            binding.chat = chat
//
//            binding.root.setOnClickListener {
//                itemClickListener.onItemClicked(chat.chatId)
//            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = DataBindingUtil.inflate<ItemChatroomBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_chatroom,
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