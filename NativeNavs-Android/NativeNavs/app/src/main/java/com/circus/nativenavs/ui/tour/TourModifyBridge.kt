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

private const val TAG = "TourModifyBridge"

class TourModifyBridge(
    private val homeActivity: HomeActivity,
    private val fragment: TourModifyFragment,
    private val webView: WebView
) {

    @JavascriptInterface
    fun navigateFromTourModifyToTourDetailFragment(tourId: Int, navId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            fragment.navigateFromTourModifyToTourDetailFragment(tourId, navId)
            Log.d(TAG, "navigateFromTourModifyToTourDetailFragment: $tourId ,$navId")
        }
    }

    @JavascriptInterface
    fun showModifyFailDialog() {
        CoroutineScope(Dispatchers.Main).launch {
            fragment.showModifyFailDialog()
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