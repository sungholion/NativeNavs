package com.circus.nativenavs.ui.home.trip

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.net.toUri
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentMyTripBinding
import com.circus.nativenavs.ui.home.HomeActivity

class MyTripFragment : BaseFragment<FragmentMyTripBinding>(FragmentMyTripBinding::bind,R.layout.fragment_my_trip){

    private lateinit var homeActivity: HomeActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onResume() {
        super.onResume()
        homeActivity.hideBottomNav(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(requireContext(), "내여행", Toast.LENGTH_SHORT).show()
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
        binding.myTripWv.loadUrl("www.naver.com")
    }

}