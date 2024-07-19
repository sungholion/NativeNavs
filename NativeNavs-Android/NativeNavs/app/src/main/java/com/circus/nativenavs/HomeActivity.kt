package com.circus.nativenavs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.circus.nativenavs.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private val binding : ActivityHomeBinding by lazy {
        DataBindingUtil.setContentView(this,R.layout.activity_home)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}