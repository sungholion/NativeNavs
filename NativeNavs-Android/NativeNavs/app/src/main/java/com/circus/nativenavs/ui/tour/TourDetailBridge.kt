package com.circus.nativenavs.ui.tour

import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.circus.nativenavs.data.UserDto
import com.circus.nativenavs.ui.home.HomeActivity
import com.google.gson.Gson

private const val TAG = "싸피_TourDetailBridge"

class TourDetailBridge(
    private val homeActivity: HomeActivity,
    private val fragment: TourDetailFragment,
    private val webView: WebView
) {
    @JavascriptInterface
    fun navigateToNavProfileFragment(navId: Int) {
        fragment.navigateToNavProfileFragment(navId)
        Log.d(TAG, "navigateToNavProfileFragment: $navId")
    }

    @JavascriptInterface
    fun navigateToReviewListFragment(tourId: Int) {
        fragment.navigateToReviewListFragment(tourId)
        Log.d(TAG, "navigateToReviewListFragment: $tourId")
    }

    fun sendUserData(user: UserDto) {
        val gson = Gson()
        val json = gson.toJson(user)
        val script = "javascript:getUserData('$json');"
        Log.d(TAG, "sendUserData: $script")
        evaluateWebViewFunction(script) { result ->
            Log.d(TAG, "sendUserData: $result")
        }

    }

    private fun evaluateWebViewFunction(
        script: String,
        callback: ((String) -> Unit)? = null,
    ) {
        return webView.evaluateJavascript(script, callback)
    }

}