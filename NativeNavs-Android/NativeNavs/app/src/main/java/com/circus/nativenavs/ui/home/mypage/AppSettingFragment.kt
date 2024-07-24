package com.circus.nativenavs.ui.home.mypage

import android.content.Context
import android.os.Bundle
import android.view.View
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentAppSettingBinding
import com.circus.nativenavs.ui.home.HomeActivity


class AppSettingFragment : BaseFragment<FragmentAppSettingBinding>(FragmentAppSettingBinding::bind,R.layout.fragment_app_setting){

    private lateinit var homeActivity: HomeActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onResume() {
        super.onResume()
        homeActivity.hideBottomNav(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}