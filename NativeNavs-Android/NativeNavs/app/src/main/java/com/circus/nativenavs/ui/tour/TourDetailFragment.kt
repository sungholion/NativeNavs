package com.circus.nativenavs.ui.tour

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
import com.circus.nativenavs.databinding.FragmentTourDetailBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.util.CustomTitleWebView
import com.circus.nativenavs.util.navigate
import com.circus.nativenavs.util.popBackStack

private const val TAG = "싸피_TourDetailFragment"

class TourDetailFragment : BaseFragment<FragmentTourDetailBinding>(
    FragmentTourDetailBinding::bind,
    R.layout.fragment_tour_detail
) {

    private lateinit var homeActivity: HomeActivity
    private val args: TourDetailFragmentArgs by navArgs()

    private lateinit var bridge: TourDetailBridge
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

    private fun initWebView() {
        binding.tourDetailWv.binding.customWebviewTitleWv.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (!isPageLoaded) {
                    isPageLoaded = true
                    bridge.sendUserData(UserDto(1, "use token", true))
                }
            }

        }

        val url = "https://i11d110.p.ssafy.io/tour/detail/${args.tourId}"
        Log.d(TAG, "initCustomView: $url")
        binding.tourDetailWv.loadWebViewUrl(url)

    }

    private fun initBridge() {
        bridge =
            TourDetailBridge(homeActivity, this, binding.tourDetailWv.binding.customWebviewTitleWv)
        binding.tourDetailWv.binding.customWebviewTitleWv.addJavascriptInterface(bridge, "Android")
    }

    private fun initCustomView() {
        binding.tourDetailWv.setOnBackListener(object : CustomTitleWebView.OnBackClickListener {
            override fun onClick() {
                popBackStack()
            }
        })

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

    fun navigateToNavProfileFragment(navId: Int) {
        val action = TourDetailFragmentDirections.actionTourDetailFragmentToProfileFragment(navId)
        navigate(action)
    }

    fun navigateToReviewListFragment(tourId: Int) {
        val action =
            TourDetailFragmentDirections.actionTourDetailFragmentToReviewListFragment(tourId)
        navigate(action)
    }

    override fun onResume() {
        super.onResume()
        homeActivity.hideBottomNav(false)
        isPageLoaded = false
    }
}
