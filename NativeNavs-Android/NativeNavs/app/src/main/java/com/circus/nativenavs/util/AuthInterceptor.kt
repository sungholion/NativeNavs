package com.circus.nativenavs.util

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class AuthInterceptor(private var authToken: String) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val builder: Request.Builder = originalRequest.newBuilder()
            .header("Authorization", "Bearer $authToken")

        val url = originalRequest.url.toString()

        // 특정 경로를 제외한 경우에만 "Content-Type" 헤더를 추가합니다.
        if (!url.contains("users/autenticateEmail") && !url.contains("users/sendEmail")) {
            builder.header("Content-Type", "application/json")
        }

        val newRequest: Request = builder.build()

        // 요청 헤더를 로그로 출력
        println("Request URL: ${newRequest.url}")
        println("Request Headers: ${newRequest.headers}")


        return chain.proceed(newRequest)
    }

    fun setAuthToken(token: String) {
        this.authToken = token
    }
}