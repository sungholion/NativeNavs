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
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {
    companion object {
        lateinit var retrofit: Retrofit
        lateinit var translationRetrofit: Retrofit
        private val authInterceptor = AuthInterceptor("")
        val gson: Gson = GsonBuilder().setLenient().create()
        fun setAuthToken(token: String) {
            authInterceptor.setAuthToken(token)
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
            } else {
                Log.e("FCM", "Fetching FCM registration token failed", task.exception)
            }
        }
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
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

        val translationClient = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val originalRequest: Request = chain.request()
                val requestWithHeaders = originalRequest.newBuilder()
                    .header("X-NCP-APIGW-API-KEY-ID", "eedtgq7ie7")
                    .header("X-NCP-APIGW-API-KEY", "K1bjIq79fFQmPsf9XsLGGvuZ0I1H4FmPrrk4Oezx")
                    .header("Content-Type", "application/json")
                    .build()
                chain.proceed(requestWithHeaders)
            }
            .addInterceptor(loggingInterceptor)
            .build()

        translationRetrofit = Retrofit.Builder()
            .baseUrl("https://naveropenapi.apigw.ntruss.com/nmt/v1/")
            .client(translationClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        WebView.setWebContentsDebuggingEnabled(true)
    }
}