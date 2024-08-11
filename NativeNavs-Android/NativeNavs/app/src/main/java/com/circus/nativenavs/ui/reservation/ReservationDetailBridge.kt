package com.circus.nativenavs.ui.reservation

import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.circus.nativenavs.data.UserDto
import com.circus.nativenavs.ui.home.HomeActivity
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReservationDetailBridge(
    private val homeActivity: HomeActivity,
    private val fragment: ReservationDetailFragment,
    private val webView: WebView
) {

    @JavascriptInterface
    fun navigateToReservationDetailChattingRoom(chatId : Int) {
        CoroutineScope(Dispatchers.Main).launch {
            fragment.navigateToReservationDetailChattingRoom(chatId)
        }
    }

    @JavascriptInterface
    fun navigateBack() {
        CoroutineScope(Dispatchers.Main).launch {
            fragment.navigateBack()
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