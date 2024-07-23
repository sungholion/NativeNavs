package com.circus.nativenavs.ui.signup

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.circus.nativenavs.R
import com.circus.nativenavs.databinding.ItemLanguageBinding

private const val TAG = "싸피_LanguageListAdapter"

class LanguageListAdapter :
    ListAdapter<String, LanguageListAdapter.LanguageViewHolder>(LanguageComparator) {

    companion object LanguageComparator : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    inner class LanguageViewHolder(val binding: ItemLanguageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(language: String) {
            binding.languageTv.text = language

            binding.root.setOnClickListener {
                binding.languageCb.isChecked = !binding.languageCb.isChecked
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val binding = DataBindingUtil.inflate<ItemLanguageBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_language,
            parent,
            false
        )

        return LanguageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface ItemClickListener {
        fun onItemClicked()
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }
}