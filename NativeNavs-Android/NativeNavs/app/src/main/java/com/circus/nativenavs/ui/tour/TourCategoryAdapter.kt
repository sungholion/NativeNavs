package com.circus.nativenavs.ui.tour

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.circus.nativenavs.R
import com.circus.nativenavs.data.CategoryDto
import com.circus.nativenavs.databinding.ItemCategoryBinding

class TourCategoryAdapter(
private val onCategoryClicked: (CategoryDto, Boolean) -> Unit
,val isKorean : Boolean) : ListAdapter<CategoryDto, TourCategoryAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {

        val binding = DataBindingUtil.inflate<ItemCategoryBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_category,
            parent,
            false
        )
        return CategoryViewHolder(binding).apply {
            setIsRecyclable(false);
        }
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }

    inner class CategoryViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: CategoryDto) {
            binding.tourSearchCategory.apply {
                textOn = if(isKorean) category.name else category.englishName
                textOff = if(isKorean) category.name else category.englishName
                isChecked = category.isChecked

                Log.d("reset", "bind: $category")

                val background = if (!category.isChecked ) {
                    R.drawable.stroke_round_circle_gray_d9d9 // or use a color
                } else {
                    R.drawable.shape_track_on // or use a color
                }
                setBackgroundResource(background)

                setOnCheckedChangeListener { _, isChecked ->
                    onCategoryClicked(category.copy(isChecked = isChecked), isChecked)
                    updateButtonBackground(isChecked)
                }

            }
        }
        private fun updateButtonBackground(isChecked: Boolean) {
            val background = if (isChecked) {
                R.drawable.shape_track_on
            } else {
                R.drawable.stroke_round_circle_gray_d9d9
            }
            binding.tourSearchCategory.setBackgroundResource(background)
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
    private class CategoryDiffCallback : DiffUtil.ItemCallback<CategoryDto>() {
        override fun areItemsTheSame(oldItem: CategoryDto, newItem: CategoryDto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CategoryDto, newItem: CategoryDto): Boolean {
            return oldItem== newItem
        }
    }
}