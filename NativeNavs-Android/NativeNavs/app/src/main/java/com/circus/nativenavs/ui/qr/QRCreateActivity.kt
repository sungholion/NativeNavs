package com.circus.nativenavs.ui.qr

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseActivity
import com.circus.nativenavs.databinding.ActivityQrcreateBinding
import com.circus.nativenavs.util.generateQRCode

class QRCreateActivity : BaseActivity<ActivityQrcreateBinding>(ActivityQrcreateBinding::inflate) {

    private var qrText =  "Hello, QR Code!"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.action?.let {
            qrText = it
        }

        val qrCodeBitmap = generateQRCode(qrText, 512, 512)
       binding.qrCodeImageView.setImageBitmap(qrCodeBitmap)
    }
}