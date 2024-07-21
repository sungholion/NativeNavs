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
    }
}