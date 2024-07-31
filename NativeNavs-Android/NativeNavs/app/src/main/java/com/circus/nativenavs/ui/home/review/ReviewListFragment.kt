package com.circus.nativenavs.ui.home.review

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.navArgs
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.data.UserDto
import com.circus.nativenavs.databinding.FragmentReviewListBinding
import com.circus.nativenavs.util.CustomTitleWebView
import com.circus.nativenavs.util.popBackStack
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.ui.tour.TourDetailBridge
import com.circus.nativenavs.ui.tour.TourDetailFragmentArgs

class ReviewListFragment : BaseFragment<FragmentReviewListBinding>(
    FragmentReviewListBinding::bind,
    R.layout.fragment_review_list
) {

    private lateinit var homeActivity: HomeActivity
    private val args: ReviewListFragmentArgs by navArgs()

    //    private lateinit var bridge: bridge class 지정
    private var isPageLoaded = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBridge()
        initCustomView()
        initWebView()
    }

    private fun initBridge() {
//        bridge =
//
//        binding.reviewCustomWv.binding.customWebviewTitleWv.addJavascriptInterface(bridge, "Android")
    }

    private fun initWebView() {
//        binding.reviewCustomWv.binding.customWebviewTitleWv.webViewClient = object : WebViewClient() {
//            override fun onPageFinished(view: WebView?, url: String?) {
//                super.onPageFinished(view, url)
//                if (!isPageLoaded) {
//                    isPageLoaded = true
//                    bridge.sendUserData(UserDto(1, "use token", true))
//                }
//            }
//
//        }
//
//        val url = "~~~~"
//        Log.d(TAG, "initCustomView: $url")
//        binding.reviewCustomWv.loadWebViewUrl(url)

    }

    private fun initCustomView() {
        binding.reviewCustomWv.setOnBackListener(object : CustomTitleWebView.OnBackClickListener {
            override fun onClick() {
                popBackStack()
            }

        })

        homeActivity.onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!binding.reviewCustomWv.backWebView()) {
                        popBackStack()
                    }
                }
            })

    }

    override fun onResume() {
        super.onResume()
        homeActivity.hideBottomNav(false)
        isPageLoaded = false
    }
}
