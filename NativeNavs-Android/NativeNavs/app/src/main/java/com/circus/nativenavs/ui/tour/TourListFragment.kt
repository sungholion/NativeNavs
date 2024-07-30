package com.circus.nativenavs.ui.tour

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class TourListFragment : BaseFragment<FragmentTourListBinding>(
    FragmentTourListBinding::bind,
    R.layout.fragment_tour_list
) {

    private lateinit var homeActivity: HomeActivity
    private lateinit var bridge: TourListBridge

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEvent()
        initWebView()
        initBridge()
        CoroutineScope(Dispatchers.Main).launch {
            Log.d("싸피", "onViewCreated: ------start")
            delay(3000)
            Log.d("싸피", "onViewCreated: ------mid")
            bridge.sendUserData(UserDto(1,"use token", true))
//            bridge.showWebViewAlert("잘 가니?")
            Log.d("싸피", "onViewCreated: ------end")

        }


        binding.tourSearchBtn.setOnClickListener {
//            bridge.showWebViewAlert("잘 가니?")
        }
    }

    private fun initBridge() {
        bridge = TourListBridge(homeActivity, this, binding.tourListWv)
        binding.tourListWv.addJavascriptInterface(bridge, "TourListBridge")

//        bridge.sendUserData(UserDto(1,"use token", true))
//        bridge.showWebViewAlert("잘 가니?")
//        bridge.showAlert("잘 가니?")
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

        binding.tourListWv.webViewClient = WebViewClient()
        binding.tourListWv.webChromeClient = WebChromeClient()

//        binding.tourListWv.loadUrl("http://192.168.1.10:3000")
        binding.tourListWv.loadUrl("http://i11d110.p.ssafy.io/main")
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
