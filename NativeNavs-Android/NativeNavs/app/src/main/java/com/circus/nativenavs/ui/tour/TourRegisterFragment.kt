package com.circus.nativenavs.ui.tour

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavDirections
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.data.UserDto
import com.circus.nativenavs.databinding.FragmentTourRegisterBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.util.CustomTitleWebView
import com.circus.nativenavs.util.SharedPref
import com.circus.nativenavs.util.navigate
import com.circus.nativenavs.util.popBackStack

private const val TAG = "TourRegisterFragment"

class TourRegisterFragment : BaseFragment<FragmentTourRegisterBinding>(
    FragmentTourRegisterBinding::bind,
    R.layout.fragment_tour_register
) {

    private lateinit var homeActivity: HomeActivity
    private var isPageLoaded = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onResume() {
        super.onResume()
        homeActivity.hideBottomNav(false)
        isPageLoaded = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCustomView()
        initWebView()
    }

    private fun initCustomView() {
        binding.tourRegisterWv.setOnBackListener(object : CustomTitleWebView.OnBackClickListener {
            override fun onClick() {
                popBackStack()
            }
        })

        homeActivity.onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!binding.tourRegisterWv.backWebView()) {
                        popBackStack()
                    }
                }
            })

    }

    private fun initWebView() {
        binding.tourRegisterWv.binding.customWebviewTitleWv.webViewClient =
            object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    if (!isPageLoaded) {
                        isPageLoaded = true
//                    bridge.sendUserData(UserDto(1, "use token", true))
                    }
                }

            }

        val url = "https://i11d110.p.ssafy.io/tour/create"
        Log.d(TAG, "initCustomView: $url")
        binding.tourRegisterWv.loadWebViewUrl(url)

    }

}