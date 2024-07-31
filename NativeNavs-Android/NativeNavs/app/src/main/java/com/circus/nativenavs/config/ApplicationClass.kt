package com.circus.nativenavs.config

import android.app.Application
import android.util.Patterns
import com.circus.nativenavs.util.AuthInterceptor
import com.circus.nativenavs.util.NATIVENAVS_URL
import java.util.regex.Pattern
import com.circus.nativenavs.util.PREF
import com.circus.nativenavs.util.SharedPref
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
    }

    override fun onCreate() {
        super.onCreate()

        SharedPref.sharedPrefs = applicationContext.getSharedPreferences(PREF, MODE_PRIVATE)
        initRetrofitInstance()
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
    }

}