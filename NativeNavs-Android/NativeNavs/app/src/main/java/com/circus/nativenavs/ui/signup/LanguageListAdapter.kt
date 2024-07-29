package com.circus.nativenavs.ui.signup

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.circus.nativenavs.R
import com.circus.nativenavs.data.LanguageDTO
import com.circus.nativenavs.databinding.ItemLanguageBinding

private const val TAG = "싸피_LanguageListAdapter"

class LanguageListAdapter(private val onLanguageCheckedChange: (String, Boolean) -> Unit) :
    ListAdapter<LanguageDTO, LanguageListAdapter.LanguageViewHolder>(LanguageComparator) {

    companion object LanguageComparator : DiffUtil.ItemCallback<LanguageDTO>() {
        override fun areItemsTheSame(oldItem: LanguageDTO, newItem: LanguageDTO): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: LanguageDTO, newItem: LanguageDTO): Boolean {
            return oldItem == newItem
        }

        const val MAX_COUNT = 5

    }

    private var checkedItemCount = 0

    inner class LanguageViewHolder(val binding: ItemLanguageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(languageItem: LanguageDTO) {
            binding.languageTv.text = languageItem.language
            binding.languageCb.isChecked = languageItem.isChecked


            binding.root.setOnClickListener {
                // Only proceed if checkedItemCount is less than maxCheckedCount
                if (languageItem.isChecked || checkedItemCount < MAX_COUNT) {
                    languageItem.isChecked = !languageItem.isChecked
                    binding.languageCb.isChecked = languageItem.isChecked
                    if (languageItem.isChecked) {
                        checkedItemCount++
                    } else {
                        checkedItemCount--
                    }
                    onLanguageCheckedChange(languageItem.language, languageItem.isChecked)
                }
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