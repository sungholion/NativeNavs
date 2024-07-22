package com.circus.nativenavs.ui.login

import android.content.Intent
import android.os.Bundle
import com.circus.nativenavs.config.BaseActivity
import com.circus.nativenavs.databinding.ActivityLoginBinding
import com.circus.nativenavs.ui.home.HomeActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

    }
}