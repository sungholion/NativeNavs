package com.circus.nativenavs.ui.login

import android.content.Intent
import android.os.Bundle
import com.circus.nativenavs.config.BaseActivity
import com.circus.nativenavs.databinding.ActivityLoginBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.ui.signup.SignUpActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initEvent()

    }

    private fun initEvent() {
        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        binding.loginSignupTv.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}