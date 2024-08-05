package com.circus.nativenavs.ui.mypage

import android.content.Context
import android.os.Bundle
import android.view.View
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentStampDetailBinding
import com.circus.nativenavs.ui.home.HomeActivity

class StampDetailFragment : BaseFragment<FragmentStampDetailBinding>(FragmentStampDetailBinding::bind,R.layout.fragment_stamp_detail) {

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