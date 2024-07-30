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

        val newRequest: Request = builder.build()
        return chain.proceed(newRequest)
    }

    fun setAuthToken(token: String) {
        this.authToken = token
    }
}