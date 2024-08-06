package com.circus.nativenavs.ui.review

import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.circus.nativenavs.data.UserDto
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.ui.tour.TourRegisterFragment
import com.google.gson.Gson

private const val TAG = "ReviewRegisterBridge"

class ReviewRegisterBridge(
    private val homeActivity: HomeActivity,
    private val fragment: ReviewRegisterFragment,
    private val webView: WebView
) {

    @JavascriptInterface
    fun moveFromReviewRegisterToReviewListFragment(tourId: Int) {
        fragment.moveFromReviewRegisterToReviewListFragment(tourId)
        Log.d(TAG, "moveFromReviewRegisterToReviewListFragment: $tourId")
    }

    @JavascriptInterface
    fun showReviewRegisterFailDialog() {
        fragment.showReviewRegisterFailDialog()
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