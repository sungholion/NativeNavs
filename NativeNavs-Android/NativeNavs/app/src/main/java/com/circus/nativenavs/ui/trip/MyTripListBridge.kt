package com.circus.nativenavs.ui.trip

import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.circus.nativenavs.ui.home.HomeActivity

private const val TAG = "MyTripListBridge"

class MyTripListBridge(
    private val homeActivity: HomeActivity,
    private val fragment: MyTripFragment,
    private val webView: WebView
) {

    @JavascriptInterface
    fun navigateToMyTripDetailFragment(navId: Int) {
        fragment.navigateToMyTripDetailFragment(navId)
        Log.d(TAG, "navigateToMyTripDetailFragment: $navId")
    }
}