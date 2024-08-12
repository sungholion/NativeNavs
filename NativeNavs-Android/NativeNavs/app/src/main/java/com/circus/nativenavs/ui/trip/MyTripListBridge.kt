package com.circus.nativenavs.ui.trip

import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.circus.nativenavs.ui.home.HomeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "MyTripListBridge"

class MyTripListBridge(
    private val homeActivity: HomeActivity,
    private val fragment: MyTripFragment,
    private val webView: WebView
) {

    @JavascriptInterface
    fun navigateToMyTripDetailFragment(tourId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            fragment.navigateToMyTripDetailFragment(tourId)
            Log.d(TAG, "navigateToMyTripDetailFragment: $tourId")
        }
    }
    @JavascriptInterface
    fun navigateToMyTripListToTourRegisterFragment(){
        CoroutineScope(Dispatchers.Main).launch {
            fragment.navigateToMyTripListToTourRegisterFragment()
        }
    }
}