package com.circus.nativenavs.data.service

import com.circus.nativenavs.data.RequestTranslate
import com.circus.nativenavs.data.ResponseTranslate
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface TranslateService {

    @POST("translation")
    suspend fun getTranslatedMessage(
        @Body message: RequestTranslate
    ): ResponseTranslate

}