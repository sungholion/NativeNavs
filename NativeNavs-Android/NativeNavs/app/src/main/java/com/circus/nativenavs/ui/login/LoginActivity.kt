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

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
    private val activityViewModel: LoginActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(SharedPref.userId != 0 && SharedPref.accessToken != null){
            ApplicationClass.setAuthToken(SharedPref.accessToken.toString())
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
        Log.d("aa", "onCreate: ")
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