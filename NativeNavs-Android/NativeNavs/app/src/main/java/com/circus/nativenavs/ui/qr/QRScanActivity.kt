package com.circus.nativenavs.ui.qr

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseActivity
import com.circus.nativenavs.databinding.ActivityQrscanBinding
import com.google.zxing.client.android.Intents.Scan
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class QRScanActivity : BaseActivity<ActivityQrscanBinding>(ActivityQrscanBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.scanBtn.setOnClickListener {
            scanCode()
        }
    }

    private fun scanCode(){
        IntentIntegrator(this).apply {
            setOrientationLocked(true)
            setPrompt("Scan a QR Code")
            setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val intentResult : IntentResult? = IntentIntegrator.parseActivityResult(requestCode,resultCode,data)
        if(intentResult != null)
        {
            intentResult.contents?.let {
                binding.scanResult.text = it
            }
        }
        else super.onActivityResult(requestCode, resultCode, data)

    }
}