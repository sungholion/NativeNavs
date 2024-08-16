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

private const val TAG = "WishListBridge"

class WishListBridge(
    private val homeActivity: HomeActivity,
    private val fragment: TourWishListFragment,
    private val webView: WebView
) {

    @JavascriptInterface
    fun navigateToWishDetailFragment(tourId: Int, navId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            fragment.navigateToWishDetailFragment(tourId, navId)
        }
    }

    @JavascriptInterface
    fun navigateFromWishToTourListFragment() {
        CoroutineScope(Dispatchers.Main).launch {
            fragment.navigateFromWishToTourListFragment()
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