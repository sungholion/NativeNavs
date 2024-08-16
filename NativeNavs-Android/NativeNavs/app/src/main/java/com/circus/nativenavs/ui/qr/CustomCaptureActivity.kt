package com.circus.nativenavs.ui.qr

import android.content.pm.ActivityInfo
import android.os.Bundle
import com.circus.nativenavs.R
import com.journeyapps.barcodescanner.CaptureActivity
import com.journeyapps.barcodescanner.ViewfinderView

class CustomCaptureActivity: CaptureActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onResume() {
        super.onResume()

        val viewfinderView = findViewById<ViewfinderView>(R.id.viewfinder_view)
        viewfinderView?.let {
            val width = resources.displayMetrics.widthPixels
            it.layoutParams = it.layoutParams.apply {
                height = width
            }
        }
    }
}