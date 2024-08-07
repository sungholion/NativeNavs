package com.circus.nativenavs.ui.login

import android.content.Intent
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (SharedPref.userId != 0 && SharedPref.accessToken != null) {
            Log.d(TAG, "onCreate: ${SharedPref.fcmToken}")
            Log.d(TAG, "onCreate: ${FirebaseMessaging.getInstance().getToken()}")
            activityViewModel.getAccessToken()
        }

        activityViewModel.autoLogin.observe(this) { statusCode ->
            when (statusCode) {
                400 -> {
                    showToast("자동 로그인 기한 만료")
                }

                200 -> {
                    ApplicationClass.setAuthToken(SharedPref.accessToken!!)
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }
            }

        }

        initEvent()

        activityViewModel.loginStatusCode.observe(this, Observer { statusCode ->
            // 상태 코드 처리
            if (statusCode == 200) {
                showToast("로그인 성공")
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                showToast("로그인 실패")
            }
        })


    }

    private fun initEvent() {
        binding.loginBtn.setOnClickListener {
            val email = binding.loginEmailEt.text.toString()
            val password = binding.loginPwEt.text.toString()
            val device = ""

            activityViewModel.Login(LoginDto(email, password, device))
        }

        binding.loginSignupTv.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}