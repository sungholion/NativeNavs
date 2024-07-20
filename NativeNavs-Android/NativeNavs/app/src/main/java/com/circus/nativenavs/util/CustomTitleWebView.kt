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
        binding.customTitleWv.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            setSupportMultipleWindows(true)
            loadWithOverviewMode = true
            useWideViewPort = true
            setSupportZoom(false)
            domStorageEnabled = true
        }

        binding.customTitleWv.webViewClient = WebViewClient()
        binding.customTitleWv.webChromeClient = WebChromeClient()
    }

    private fun initEvent() {
        binding.customTitleBackIv.setOnClickListener {
            onBackClickListener?.onClick()
        }
    }

    fun backWebView(): Boolean {
        binding.customTitleWv.apply {
            if (this.canGoBack()) {
                this.goBack()
                return true
            } else {
                return false
            }
        }
    }

    fun loadWebViewUrl(url: String) {
        binding.customTitleWv.loadUrl(url)
    }

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleWebView)
        setTypedArray(typedArray)

    }

    private fun setTypedArray(typedArray: TypedArray) {
        binding.customTitleTv.text = typedArray.getText(R.styleable.CustomTitleWebView_title)
        typedArray.getString(R.styleable.CustomTitleWebView_url)
            ?.let { binding.customTitleWv.loadUrl(it) }
    }

    fun setOnBackListener(listener: OnBackClickListener) {
        onBackClickListener = listener
    }

    private var onBackClickListener: OnBackClickListener? = null

    interface OnBackClickListener {
        fun onClick()
    }
}