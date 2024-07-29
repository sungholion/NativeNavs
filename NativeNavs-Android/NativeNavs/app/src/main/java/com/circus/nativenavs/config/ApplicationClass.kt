package com.circus.nativenavs.config

import android.app.Application
import android.util.Patterns
import com.circus.nativenavs.util.SharedPreferenceController
import java.util.regex.Pattern

class ApplicationClass : Application() {
    companion object {
        lateinit var sharedPreferences: SharedPreferenceController

        const val FRAGMENT_TOUR_LIST = 10
        const val FRAGMENT_TOUR_DETAIL = 11
        const val FRAGMENT_TOUR_REGISTER = 12

    }

    override fun onCreate() {
        super.onCreate()

        sharedPreferences = SharedPreferenceController(applicationContext)

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