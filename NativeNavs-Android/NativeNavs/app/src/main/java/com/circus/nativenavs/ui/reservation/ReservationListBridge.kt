package com.circus.nativenavs.ui.reservation

import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.circus.nativenavs.data.UserDto
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.ui.review.ReviewListFragment
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReservationListBridge (
    private val homeActivity: HomeActivity,
    private val fragment: ReservationListFragment,
    private val webView: WebView
) {

    @JavascriptInterface
    fun navigateToReservationListFragmentReservationDetail(tourId: Int, reservationId : Int) {
        CoroutineScope(Dispatchers.Main).launch {
            fragment.navigateToReservationListFragmentReservationDetail(tourId,reservationId)
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