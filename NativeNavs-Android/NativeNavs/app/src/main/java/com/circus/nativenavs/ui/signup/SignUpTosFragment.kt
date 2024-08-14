package com.circus.nativenavs.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentSignUpTosBinding
import com.circus.nativenavs.util.WEBURL
import com.circus.nativenavs.util.popBackStack

class SignUpTosFragment : BaseFragment<FragmentSignUpTosBinding>(
    FragmentSignUpTosBinding::bind,
    R.layout.fragment_sign_up_tos
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initWebView()
        initEvent()
    }

    private fun initWebView() {
        binding.signupTosWv.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            setSupportMultipleWindows(true)
            loadWithOverviewMode = true
            useWideViewPort = true
            setSupportZoom(false)
            domStorageEnabled = true
        }
        binding.signupTosWv.webViewClient = WebViewClient()
        binding.signupTosWv.webChromeClient = WebChromeClient()
        binding.signupTosWv.loadUrl(WEBURL + "privacy_terms")
    }

    private fun initEvent() {
        binding.signupTosCheckBtn.setOnClickListener {
            popBackStack()
        }
    }

}