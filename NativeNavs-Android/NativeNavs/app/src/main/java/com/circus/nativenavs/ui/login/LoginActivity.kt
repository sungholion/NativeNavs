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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initNewtWorkCheck()
        initIntent()
        autoLogin()
        initEvent()
        initObserve()
    }

    private fun initIntent() {
        homeActivityIntent = Intent(this, HomeActivity::class.java).apply {
            if (intent != null) {
                putExtra("flag", intent.getIntExtra("flag", -1))
                putExtra("roomId", intent.getIntExtra("roomId", -1))
                putExtra("reservationId", intent.getIntExtra("reservationId", -1))
                putExtra("tourId", intent.getIntExtra("tourId", -1))
            }
        }
    }

    private fun autoLogin() {
        if (SharedPref.userId != 0 && SharedPref.accessToken != null) {
            Log.d(TAG, "onCreate: ${SharedPref.fcmToken}")
            Log.d(TAG, "onCreate: ${FirebaseMessaging.getInstance().getToken()}")
            activityViewModel.getAccessToken()
        }
    }

    private fun initObserve() {
        activityViewModel.autoLogin.observe(this) { statusCode ->
            when (statusCode) {
                400 -> {
                    showToast("자동 로그인 기한 만료")
                }

                200 -> {
                    ApplicationClass.setAuthToken(SharedPref.accessToken!!)
                    startActivity(homeActivityIntent)
                    finish()
                }
            }

        }

        activityViewModel.loginStatusCode.observe(this) { statusCode ->
            // 상태 코드 처리
            if (statusCode == 200) {
                showToast("로그인 성공")
                startActivity(homeActivityIntent)
                finish()
            } else {
                showToast("로그인 실패")
            }
        }
    }

    private fun initNewtWorkCheck(){
        if (!isNetworkAvailable()) {
            showToast("인터넷에 연결되어 있지 않습니다.")
            finish() // 앱 종료
        }
    }
    @SuppressLint("ServiceCast")
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return networkCapabilities != null && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
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