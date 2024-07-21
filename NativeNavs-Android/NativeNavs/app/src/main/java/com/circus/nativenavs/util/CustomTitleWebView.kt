package com.circus.nativenavs.util

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.constraintlayout.widget.ConstraintLayout
import com.circus.nativenavs.R
import com.circus.nativenavs.databinding.CustomTitleWebviewBinding

class CustomTitleWebView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private lateinit var binding: CustomTitleWebviewBinding

    init {
        initView()
        initEvent()
        getAttrs(attrs)
    }

    private fun initView() {
        binding = CustomTitleWebviewBinding.inflate(LayoutInflater.from(context), this, true)

        initWebView()
    }

    private fun initWebView() {
        binding.customWebviewTitleWv.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            setSupportMultipleWindows(true)
            loadWithOverviewMode = true
            useWideViewPort = true
            setSupportZoom(false)
            domStorageEnabled = true
        }

        binding.customWebviewTitleWv.webViewClient = WebViewClient()
        binding.customWebviewTitleWv.webChromeClient = WebChromeClient()
    }

    private fun initEvent() {
        binding.customWebviewTitleBackIv.setOnClickListener {
            onBackClickListener?.onClick()
        }
    }

    fun backWebView(): Boolean {
        binding.customWebviewTitleWv.apply {
            if (this.canGoBack()) {
                this.goBack()
                return true
            } else {
                return false
            }
        }
    }

    fun loadWebViewUrl(url: String) {
        binding.customWebviewTitleWv.loadUrl(url)
    }

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleWebView)
        setTypedArray(typedArray)

    }

    private fun setTypedArray(typedArray: TypedArray) {
        binding.customWebviewTitleTv.text = typedArray.getText(R.styleable.CustomTitleWebView_title)
        typedArray.getString(R.styleable.CustomTitleWebView_url)
            ?.let { binding.customWebviewTitleWv.loadUrl(it) }
    }

    fun setOnBackListener(listener: OnBackClickListener) {
        onBackClickListener = listener
    }

    private var onBackClickListener: OnBackClickListener? = null

    interface OnBackClickListener {
        fun onClick()
    }
}