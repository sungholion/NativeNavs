package com.circus.nativenavs.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.circus.nativenavs.config.ApplicationClass
import com.circus.nativenavs.config.BaseActivity
import com.circus.nativenavs.data.LoginDto
import com.circus.nativenavs.databinding.ActivityLoginBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.ui.signup.SignUpActivity
import com.circus.nativenavs.util.SharedPref
import com.google.firebase.messaging.FirebaseMessaging

private const val TAG = "LoginActivity"

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
    private val activityViewModel: LoginActivityViewModel by viewModels()
    private lateinit var homeActivityIntent: Intent
    private var isClicked = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initEvent()
        initObserve()
    }

    private fun initObserve() {

        activityViewModel.loginStatusCode.observe(this) { statusCode ->
            // 상태 코드 처리
            if (statusCode == 200) {
                isClicked = false
                showToast("로그인 성공")
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                isClicked = false
                showToast("로그인 실패")
            }
        }
    }

    private fun initEvent() {
        binding.loginBtn.setOnClickListener {
            if(!isClicked){
                val email = binding.loginEmailEt.text.toString()
                val password = binding.loginPwEt.text.toString()
                val device = ""

                activityViewModel.Login(LoginDto(email, password, device))
                isClicked = true
            }
            else showToast("잠시만 기다려 주세요")
        }

        binding.loginSignupTv.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}