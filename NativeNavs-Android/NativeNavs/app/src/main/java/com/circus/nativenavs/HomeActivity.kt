package com.circus.nativenavs

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.circus.nativenavs.databinding.ActivityHomeBinding
import com.circus.nativenavs.util.CustomTitleWebView
import com.circus.nativenavs.util.StyleActivity

class HomeActivity : AppCompatActivity() {

    private val binding : ActivityHomeBinding by lazy {
        DataBindingUtil.setContentView(this,R.layout.activity_home)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.homeCustomWv.setOnBackListener(object : CustomTitleWebView.OnBackClickListener{
            override fun onClick() {
                val intent = Intent(this@HomeActivity, StyleActivity::class.java)
                startActivity(intent)
            }
        })

        binding.homeCustomWv.loadWebViewUrl("www.google.com")

        this.onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!binding.homeCustomWv.backWebView()){
                    finish()
                }
            }
        })
    }
}