package com.circus.nativenavs.ui.reservation

import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.circus.nativenavs.data.UserDto
import com.circus.nativenavs.ui.home.HomeActivity
import com.google.gson.Gson

class ReservationRegisterBridge (
    private val homeActivity: HomeActivity,
    private val fragment: ReservationRegisterFragment,
    private val webView: WebView
) {

    @JavascriptInterface
    fun navigateToReservationRegisterChattingRoom() {
        fragment.navigateToReservationRegisterChattingRoom()
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