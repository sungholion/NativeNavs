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

private const val TAG = "싸피_TourDetailBridge"

class TourDetailBridge(
    private val homeActivity: HomeActivity,
    private val fragment: TourDetailFragment,
    private val webView: WebView
) {
    @JavascriptInterface
    fun navigateToNavProfileFragment(navId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            fragment.navigateToNavProfileFragment(navId)
            Log.d(TAG, "navigateToNavProfileFragment: $navId")
        }

    }

    @JavascriptInterface
    fun navigateToReviewListFragment(tourId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            fragment.navigateToReviewListFragment(tourId)
            Log.d(TAG, "navigateToReviewListFragment: $tourId")
        }
    }

    @JavascriptInterface
    fun navigateToTourModifyFragment(tourId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            fragment.navigateToTourModifyFragment(tourId)
            Log.d(TAG, "navigateToTourModifyFragment: $tourId")
        }
    }

    @JavascriptInterface
    fun navigateToTourListFragment() {
        CoroutineScope(Dispatchers.Main).launch {
            fragment.navigateToTourListFragment()
        }
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