package com.circus.nativenavs.ui.tour

import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.circus.nativenavs.data.UserDto
import com.circus.nativenavs.ui.home.HomeActivity
import com.google.gson.Gson

private const val TAG = "싸피_TourListBridge"
class TourListBridge(
    private val homeActivity: HomeActivity,
    private val fragment: TourListFragment,
    private val webView: WebView
) {
    @JavascriptInterface
    fun showToast(text: String){
        homeActivity.showToast(text)
    }

    @JavascriptInterface
    fun navigateToTourDetailFragment(tourId: Int) {
        fragment.moveToTourDetailFragment(tourId)
        Log.d(TAG, "navigateToTourDetailFragment: $tourId")
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