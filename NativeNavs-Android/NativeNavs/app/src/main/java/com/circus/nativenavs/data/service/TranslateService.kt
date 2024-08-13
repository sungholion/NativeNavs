package com.circus.nativenavs.data.service

import com.circus.nativenavs.data.RequestTranslate
import com.circus.nativenavs.data.ResponseTranslate
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface TranslateService {

    @POST("translation")
    suspend fun getTranslatedMessage(
        @Header("X-NCP-APIGW-API-KEY-ID") token: String?,
        @Header("X-NCP-APIGW-API-KEY") secretToken: String?,
        @Body message: RequestTranslate
    ): ResponseTranslate

}