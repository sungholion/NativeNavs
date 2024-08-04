package com.circus.nativenavs.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.circus.nativenavs.R
import com.circus.nativenavs.data.ChatRoomDto
import com.circus.nativenavs.databinding.ItemChatroomBinding

class ChatListAdapter : ListAdapter<ChatRoomDto, ChatListAdapter.ChatViewHolder>(ChatComparator) {

    companion object ChatComparator : DiffUtil.ItemCallback<ChatRoomDto>() {
        override fun areItemsTheSame(oldItem: ChatRoomDto, newItem: ChatRoomDto): Boolean {
            return oldItem.chatId == newItem.chatId
        }

        override fun areContentsTheSame(oldItem: ChatRoomDto, newItem: ChatRoomDto): Boolean {
            return oldItem == newItem
        }

    }

    inner class ChatViewHolder(val binding: ItemChatroomBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(chatRoom: ChatRoomDto) {
            binding.chat = chatRoom

            binding.root.setOnClickListener {
                itemClickListener.onItemClicked(chatRoom.chatId)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = DataBindingUtil.inflate<ItemChatroomBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_chatroom,
            parent,
            false
        )

        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface ChatItemClickListener {
        fun onItemClicked(chatRoomId: Int)
    }

    private lateinit var itemClickListener: ChatItemClickListener

    fun setItemClickListener(itemClickListener: ChatItemClickListener) {
        this.itemClickListener = itemClickListener
    }

}