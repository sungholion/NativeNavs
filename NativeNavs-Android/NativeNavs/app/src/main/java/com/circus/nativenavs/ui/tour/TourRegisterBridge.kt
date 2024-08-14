package com.circus.nativenavs.ui.tour

import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.circus.nativenavs.data.UserDto
import com.circus.nativenavs.ui.home.HomeActivity
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "TourRegisterBridge"

class TourRegisterBridge(
    private val homeActivity: HomeActivity,
    private val fragment: TourRegisterFragment,
    private val webView: WebView
) {

    @JavascriptInterface
    fun moveFromTourRegisterToTourDetailFragment(tourId: Int, navId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            fragment.moveFromTourRegisterToTourDetailFragment(tourId, navId)
        }
    }

    @JavascriptInterface
    fun showRegisterFailDialog() {
        CoroutineScope(Dispatchers.Main).launch {
            fragment.showRegisterFailDialog()
        }
    }

    fun sendUserData(user: UserDto) {
        val gson = Gson()
        val json = gson.toJson(user)
        val script = "javascript:getUserData('$json');"
        evaluateWebViewFunction(script) { result ->
        }

    }

    private fun evaluateWebViewFunction(
        script: String,
        callback: ((String) -> Unit)? = null,
    ) {
        return webView.evaluateJavascript(script, callback)
    }
}