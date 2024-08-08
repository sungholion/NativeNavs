package com.circus.nativenavs.config

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import android.webkit.WebView
import android.util.Patterns
import com.circus.nativenavs.util.AuthInterceptor
import com.circus.nativenavs.util.NATIVENAVS_URL
import java.util.regex.Pattern
import com.circus.nativenavs.util.PREF
import com.circus.nativenavs.util.SharedPref
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {
    companion object {
        lateinit var retrofit: Retrofit
        private val authInterceptor = AuthInterceptor("")
        val gson: Gson = GsonBuilder().setLenient().create()
        fun setAuthToken(token: String) {
            authInterceptor.setAuthToken(token)
        }

        // Notification Channel ID
        const val channel_id = "NativeNavs_Channel"

        // ratrofit  수업 후 network 에 업로드 할 수 있도록 구성
        fun uploadToken(token: String) {


        }
    }

    override fun onCreate() {
        super.onCreate()

        SharedPref.sharedPrefs = applicationContext.getSharedPreferences(PREF, MODE_PRIVATE)
        initRetrofitInstance()
        FirebaseApp.initializeApp(this)
        initFcmToken()
    }

    private fun initFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                SharedPref.fcmToken = token
                Log.d("FCM", "initFcmToken: $token")
            } else {
                Log.w("FCM", "Fetching FCM registration token failed", task.exception)
            }
        }
    }

    // Notification 수신을 위한 채널 추가
    private fun createNotificationChannel(id: String, name: String) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(NotificationChannel(id, name, importance))
    }

    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private fun initRetrofitInstance() {
        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(NATIVENAVS_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        WebView.setWebContentsDebuggingEnabled(true)

//        initRetrofitInstance()
    }

//    // retrofit 인스턴스 생성, 레트로핏에 설정값 지정
//    private fun initRetrofitInstance() {
//        val client: OkHttpClient = OkHttpClient.Builder()
//            .addInterceptor { chain ->
//                val request = chain.request().newBuilder()
//                    .addHeader("Authorization", "Bearer $GPT_KEY")
//                    .build()
//                chain.proceed(request)
//            }
//            .addInterceptor(HttpLoggingInterceptor().apply {
//                level = HttpLoggingInterceptor.Level.BODY
//            })
//            .readTimeout(30, TimeUnit.SECONDS)
//            .connectTimeout(30, TimeUnit.SECONDS)
//            .build()
//
//        // retrofit 전역변수에 API url, 인터셉터, Gson 넣어주고 빌드
//        GPTRetrofit = Retrofit.Builder()
//            .baseUrl(GPT_BASE_URL)
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//
//        weatherRetrofit = Retrofit.Builder()
//            .baseUrl(WEATHER_BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//    }

//    private val gson: Gson = GsonBuilder()
//        .setLenient()
//        .create()
}