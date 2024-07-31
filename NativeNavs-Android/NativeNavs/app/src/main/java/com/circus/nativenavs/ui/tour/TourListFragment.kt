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
import com.circus.nativenavs.databinding.FragmentTourListBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.ui.video.VideoActivity
import com.circus.nativenavs.util.TourListBridge
import com.circus.nativenavs.util.navigate

class TourListFragment : BaseFragment<FragmentTourListBinding>(
    FragmentTourListBinding::bind,
    R.layout.fragment_tour_list
) {

    private lateinit var homeActivity: HomeActivity
    private lateinit var bridge: TourListBridge
    private var isPageLoaded = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEvent()
        initWebView()
        initBridge()
    }

    private fun initBridge() {
        bridge = TourListBridge(homeActivity, this, binding.tourListWv)
        binding.tourListWv.addJavascriptInterface(bridge, "TourListBridge")
    }

    private fun initEvent() {
        binding.tourSearchBtn.setOnClickListener {
            startActivity(Intent(requireContext(), VideoActivity::class.java))
            Toast.makeText(requireContext(), "검색바 클릭", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initWebView() {
        binding.tourListWv.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            setSupportMultipleWindows(true)
            loadWithOverviewMode = true
            useWideViewPort = true
            setSupportZoom(false)
            domStorageEnabled = true
        }

        binding.tourListWv.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (!isPageLoaded) {
                    isPageLoaded = true
                    bridge.sendUserData(UserDto(1, "use token", true))
                }
            }
        }
        binding.tourListWv.webChromeClient = WebChromeClient()

        binding.tourListWv.loadUrl("https://i11d110.p.ssafy.io/main")
    }

    fun moveToTourDetailFragment(tourId: Int) {
        val action = TourListFragmentDirections.actionTourListFragmentToTourDetailFragment(tourId)
        navigate(action)
    }

    override fun onResume() {
        super.onResume()
        homeActivity.hideBottomNav(true)
    }
}
