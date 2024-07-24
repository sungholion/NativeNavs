package com.circus.nativenavs.ui.setting

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentAppSettingBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.util.popBackStack


class AppSettingFragment : BaseFragment<FragmentAppSettingBinding>(
    FragmentAppSettingBinding::bind,
    R.layout.fragment_app_setting
) {

    private lateinit var homeActivity: HomeActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEvent()
        initSpinner()

    }

    private fun initSpinner() {
        val items = arrayListOf("한국어", "English")
        val spinnerAdapter = CustomSpinnerAdapter(homeActivity, items)
        binding.settingLanguageSp.adapter = spinnerAdapter
        binding.settingLanguageSp.setSelection(1)

        val spinnerItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                spinnerAdapter.setSelectedItemPosition(position)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                spinnerAdapter.setSelectedItemPosition(1)
            }

        }

        binding.settingLanguageSp.onItemSelectedListener = spinnerItemSelectedListener
    }

    private fun initEvent() {
        binding.settingTitleLayout.customWebviewTitleBackIv.setOnClickListener {
            popBackStack()
        }
    }
}