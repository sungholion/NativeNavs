package com.circus.nativenavs.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.activity.viewModels
import com.circus.nativenavs.R
import com.circus.nativenavs.config.ApplicationClass
import com.circus.nativenavs.config.BaseActivity
import com.circus.nativenavs.databinding.ActivitySplashBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.ui.login.LoginActivity
import com.circus.nativenavs.ui.login.LoginActivityViewModel
import com.circus.nativenavs.util.SharedPref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    private val activityViewModel: LoginActivityViewModel by viewModels()
    private var isLogin = false
    private lateinit var homeActivityIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initNewtWorkCheck()
        initView()
        initIntent()
        autoLogin()

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

    private fun initView() {
        // 비디오 파일 경로 설정
        binding.splashVideo.setVideoPath("android.resource://" + packageName + "/" + R.raw.splash_version4)

        // 비디오 준비 완료 후에 비디오를 시작하고, 완료 시 이벤트 처리
        binding.splashVideo.setOnPreparedListener { mediaPlayer ->
            binding.splashVideo.start()  // 비디오 시작
            CoroutineScope(Dispatchers.Main).launch {
                delay(200)
                binding.splashVideo.setBackgroundColor(Color.TRANSPARENT)
            }

            mediaPlayer.setOnCompletionListener {
                // 비디오가 끝났을 때 LoginActivity로 전환
                if (!isLogin) {
                    startActivity(Intent(this, LoginActivity::class.java))
                } else startActivity(homeActivityIntent)
                finish()
            }
        }
    }

    private fun autoLogin() {
        if (SharedPref.userId != 0 && SharedPref.accessToken != null) {
            activityViewModel.getAccessToken()
        }

        activityViewModel.autoLogin.observe(this) { statusCode ->
            when (statusCode) {
                400 -> {
                    isLogin = false
                    showToast("자동 로그인 기한 만료")
                }

                200 -> {
                    ApplicationClass.setAuthToken(SharedPref.accessToken!!)
                    isLogin = true
                }
            }

        }
    }

    private fun initNewtWorkCheck() {
        if (!isNetworkAvailable()) {
            showToast("인터넷에 연결되어 있지 않습니다.")
            finish() // 앱 종료
        }
    }

    @SuppressLint("ServiceCast")
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return networkCapabilities != null && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }
}
