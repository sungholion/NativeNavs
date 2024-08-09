package com.circus.nativenavs.ui.tour

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.data.UserDto
import com.circus.nativenavs.databinding.FragmentTourWishListBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.ui.video.VideoActivity
import com.circus.nativenavs.util.SharedPref
import com.circus.nativenavs.util.WEBURL
import com.circus.nativenavs.util.navigate

class TourWishListFragment : BaseFragment<FragmentTourWishListBinding>(
    FragmentTourWishListBinding::bind,
    R.layout.fragment_tour_wish_list
) {

    private lateinit var homeActivity: HomeActivity
    private lateinit var bridge: WishListBridge
    private var isPageLoaded = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onResume() {
        super.onResume()
        homeActivity.hideBottomNav(true)
        isPageLoaded = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBridge()
        initWebView()
    }

    private fun initBridge() {
        bridge = WishListBridge(homeActivity, this, binding.tourWishListWv)
        binding.tourWishListWv.addJavascriptInterface(bridge, "Android")
    }

    private fun initWebView() {
        binding.tourWishListWv.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            setSupportMultipleWindows(true)
            loadWithOverviewMode = true
            useWideViewPort = true
            setSupportZoom(false)
            domStorageEnabled = true
        }

        binding.tourWishListWv.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (!isPageLoaded) {
                    isPageLoaded = true
                    bridge.sendUserData(
                        UserDto(
                            SharedPref.userId!!,
                            SharedPref.accessToken!!,
                            SharedPref.isNav!!
                        )
                    )
                }
            }
        }
        binding.tourWishListWv.webChromeClient = WebChromeClient()

        val url = WEBURL + "trav/${SharedPref.userId}/wishlist"
        binding.tourWishListWv.loadUrl(url)
    }

    fun navigateToWishDetailFragment(tourId: Int, navId: Int) {
        val action =
            TourWishListFragmentDirections.actionTourWishListFragmentToTourDetailFragment(
                tourId = tourId,
                navId = navId
            )
        navigate(action)
    }

    fun navigateFromWishToTourListFragment() {
        navigate(R.id.action_tourWishListFragment_to_tourListFragment)
    }

}