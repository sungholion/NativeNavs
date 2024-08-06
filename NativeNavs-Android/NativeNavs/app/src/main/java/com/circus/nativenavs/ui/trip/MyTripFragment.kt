package com.circus.nativenavs.ui.trip

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.net.toUri
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.data.UserDto
import com.circus.nativenavs.databinding.FragmentMyTripBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.ui.tour.TourListBridge
import com.circus.nativenavs.ui.tour.TourListFragmentDirections
import com.circus.nativenavs.ui.video.VideoActivity
import com.circus.nativenavs.util.SharedPref
import com.circus.nativenavs.util.WEBURL
import com.circus.nativenavs.util.navigate

class MyTripFragment :
    BaseFragment<FragmentMyTripBinding>(FragmentMyTripBinding::bind, R.layout.fragment_my_trip) {

    private lateinit var homeActivity: HomeActivity
    private lateinit var bridge: MyTripListBridge
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
        bridge = MyTripListBridge(homeActivity, this, binding.myTripWv)
        binding.myTripWv.addJavascriptInterface(bridge, "Android")
    }

    private fun initWebView() {
        binding.myTripWv.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            setSupportMultipleWindows(true)
            loadWithOverviewMode = true
            useWideViewPort = true
            setSupportZoom(false)
            domStorageEnabled = true
        }

        binding.myTripWv.webViewClient = WebViewClient()
        binding.myTripWv.webChromeClient = WebChromeClient()

        val url = WEBURL + "nav/${SharedPref.userId}/tourlist"
        binding.myTripWv.loadUrl(url)
    }

    fun navigateToMyTripDetailFragment(tourId: Int) {
        val action =
            MyTripFragmentDirections.actionMyTripFragmentToTourDetailFragment(
                tourId = tourId,
                navId = SharedPref.userId!!
            )
        navigate(action)
    }
}