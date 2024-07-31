package com.circus.nativenavs.ui.tour

import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.circus.nativenavs.ui.home.HomeActivity

private const val TAG = "WishListBridge"

class WishListBridge(
    private val homeActivity: HomeActivity,
    private val fragment: TourWishListFragment,
    private val webView: WebView
) {

    @JavascriptInterface
    fun navigateToWishDetailFragment(tourId: Int) {
        fragment.navigateToWishDetailFragment(tourId)
        Log.d(TAG, "navigateToWishDetailFragment: $tourId")
    }
}