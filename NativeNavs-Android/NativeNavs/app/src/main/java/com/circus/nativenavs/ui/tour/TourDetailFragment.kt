package com.circus.nativenavs.ui.tour

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.navArgs
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentTourDetailBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.util.CustomTitleWebView
import com.circus.nativenavs.util.StyleActivity
import com.circus.nativenavs.util.popBackStack

class TourDetailFragment : BaseFragment<FragmentTourDetailBinding>(
    FragmentTourDetailBinding::bind,
    R.layout.fragment_tour_detail
) {

    private lateinit var homeActivity: HomeActivity
    private val args: TourDetailFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCustomView()
    }

    private fun initCustomView() {
        binding.tourDetailWv.setOnBackListener(object : CustomTitleWebView.OnBackClickListener {
            override fun onClick() {
                popBackStack()
            }
        })

        val url = "https://i11d110.p.ssafy.io/detail/${args.tourId}"
        Log.d("initCustomView", "initCustomView: url")
        binding.tourDetailWv.loadWebViewUrl(url)

        homeActivity.onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!binding.tourDetailWv.backWebView()) {
                        popBackStack()
                    }
                }
            })
    }

    override fun onResume() {
        super.onResume()
        homeActivity.hideBottomNav(false)
    }
}
