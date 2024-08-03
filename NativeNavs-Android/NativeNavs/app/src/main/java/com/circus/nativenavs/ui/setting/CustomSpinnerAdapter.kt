package com.circus.nativenavs.ui.setting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.circus.nativenavs.R
import com.circus.nativenavs.databinding.ItemDropdownBinding
import com.circus.nativenavs.databinding.ItemDropdownSelectedBinding

class CustomSpinnerAdapter(context: Context, list: ArrayList<String>) :
    ArrayAdapter<String>(context, 0, list) {

    private val layoutInflater = LayoutInflater.from(context)
    private var selectedItemPosition = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = if (convertView == null) {
            ItemDropdownSelectedBinding.inflate(layoutInflater)
        } else {
            ItemDropdownSelectedBinding.bind(convertView)
        }

        getItem(position)?.let {
            binding.itemSelectedTv.text = it
        }
        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = if (convertView == null) {
            ItemDropdownBinding.inflate(layoutInflater)
        } else {
            ItemDropdownBinding.bind(convertView)
        }

        (parent as? ListView)?.overScrollMode = View.OVER_SCROLL_NEVER

        getItem(position)?.let {
            binding.itemTv.text = it
            if (selectedItemPosition == position) {
                binding.itemTv.setTextAppearance(R.style.medium)
            }else{
                binding.itemTv.setTextAppearance(R.style.regular)
            }
        }
        return binding.root
    }

    fun setSelectedItemPosition(position: Int) {
        selectedItemPosition = position
        notifyDataSetChanged() // 선택된 항목을 갱신하기 위해 어댑터를 다시 로드
    }
}