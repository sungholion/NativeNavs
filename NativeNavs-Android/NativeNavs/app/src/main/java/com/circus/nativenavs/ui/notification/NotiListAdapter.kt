package com.circus.nativenavs.ui.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.circus.nativenavs.R
import com.circus.nativenavs.databinding.ItemNotiBinding

class NotiListAdapter : ListAdapter<String, NotiListAdapter.NotiViewHolder>(NotiComparator) {

    companion object NotiComparator : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    inner class NotiViewHolder(val binding: ItemNotiBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(noti: String) {
            binding.itemNotiTv.text = noti

            binding.root.setOnClickListener {
                itemClickListener.onItemClicked()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotiViewHolder {
        val binding = DataBindingUtil.inflate<ItemNotiBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_noti,
            parent,
            false
        )

        return NotiViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotiViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface NotiItemClickListener {
        fun onItemClicked()
    }

    private lateinit var itemClickListener: NotiItemClickListener

    fun setItemClickListener(itemClickListener: NotiItemClickListener) {
        this.itemClickListener = itemClickListener
    }
}