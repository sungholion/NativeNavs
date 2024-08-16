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

        val url = originalRequest.url.toString()

        if(!url.contains("users/search")
            && !url.contains("users/checkDuplicated/nickname")
            && !url.contains("language")
            && !url.contains("auth/refresh")
            && !url.contains("tours/category")
            && !url.contains("reviews/guide")
            && !url.contains("reviews/user")){
            builder.header("Authorization", "Bearer $authToken")
        }

        if (!url.contains("users/autenticateEmail") && !url.contains("users/sendEmail")) {
            builder.header("Content-Type", "application/json")
        }

        val newRequest: Request = builder.build()

        println("Request URL: ${newRequest.url}")
        println("Request Headers: ${newRequest.headers}")


        return chain.proceed(newRequest)
    }

    fun setAuthToken(token: String) {
        this.authToken = token
    }
}