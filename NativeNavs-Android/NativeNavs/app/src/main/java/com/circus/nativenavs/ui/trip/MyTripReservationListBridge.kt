package com.circus.nativenavs.ui.trip

import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.circus.nativenavs.ui.home.HomeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "MyTripReservationListBr"

class MyTripReservationListBridge(
    private val homeActivity: HomeActivity,
    private val fragment: MyTripReservationListFragment,
    private val webView: WebView
) {

    @JavascriptInterface
    fun navigateToReservationDetailFragment(reservationId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            fragment.navigateToReservationDetailFragment(reservationId)
            Log.d(TAG, "navigateToReservationDetailFragment: reservationId")
        }
    }
}