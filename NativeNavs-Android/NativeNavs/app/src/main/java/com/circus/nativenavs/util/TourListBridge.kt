package com.circus.nativenavs.util

import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.circus.nativenavs.data.UserDto
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.ui.tour.TourListFragment
import com.google.gson.Gson

private const val TAG = "TourListBridge"
class TourListBridge(
    private val homeActivity: HomeActivity,
    private val fragment: TourListFragment,
    private val webView: WebView
) {

    @JavascriptInterface
    fun showToast(toast: String) {
        homeActivity.showToast(toast)
    }

    @JavascriptInterface
    fun navigateToTourDetailFragment(tourId: Int) {
        fragment.moveToTourDetailFragment(tourId)
        Log.d(TAG, "navigateToTourDetailFragment: $tourId")
    }

    fun sendUserData(user: UserDto) {
        val gson = Gson()
//        val json = gson.toJson(user).replace("'", "\\'")
        val json = gson.toJson(user)
        val script = "javascript:getUserData('$json');"
        Log.d(TAG, "sendUserData: $script")
        evaluateWebViewFunction(script) { result ->
            Log.d(TAG, "sendUserData: $result")
        }

    }

    suspend fun showWebViewAlert(text: String) {
        val script = "window.showWebViewAlert('$text');"
        Log.d("싸피", script)
        evaluateWebViewFunction(script) { result ->
            Log.d("tag_test", result)
        }
    }

    fun showAlert(text: String){
        webView.evaluateJavascript("alert('$text');", null)
    }

    private fun evaluateWebViewFunction(
        script: String,
        callback: ((String) -> Unit)? = null,
    ) {
        return webView.evaluateJavascript(script, callback)
    }

}