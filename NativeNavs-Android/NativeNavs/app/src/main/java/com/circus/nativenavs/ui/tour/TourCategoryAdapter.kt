package com.circus.nativenavs.ui.tour

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.circus.nativenavs.R
import com.circus.nativenavs.data.CategoryDto

class TourCategoryAdapter(
private val onCategoryClicked: (CategoryDto, Boolean) -> Unit
) : ListAdapter<CategoryDto, TourCategoryAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }

    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val toggleButton: ToggleButton = view.findViewById(R.id.tour_search_category)

        fun bind(category: CategoryDto) {
            toggleButton.textOn = category.name
            toggleButton.textOff = category.name
            toggleButton.isChecked = category.isChecked // Set the initial checked state

            // Update button background based on checked state
            val background = if (!category.isChecked) {
                R.drawable.stroke_round_circle_gray_d9d9 // or use a color
            } else {
                R.drawable.shape_track_on // or use a color
            }
            toggleButton.setBackgroundResource(background)

            // Handle toggle button state change
            toggleButton.setOnCheckedChangeListener { _, isChecked ->
                onCategoryClicked(category, isChecked) // Call the callback with the updated state
                // Update button background based on the new checked state
                val newBackground = if (!isChecked) {
                    R.drawable.stroke_round_circle_gray_d9d9
                } else {
                    R.drawable.shape_track_on
                }
                toggleButton.setBackgroundResource(newBackground)
            }
        }
    }

    private class CategoryDiffCallback : DiffUtil.ItemCallback<CategoryDto>() {
        override fun areItemsTheSame(oldItem: CategoryDto, newItem: CategoryDto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CategoryDto, newItem: CategoryDto): Boolean {
            return oldItem == newItem
        }
    }
}