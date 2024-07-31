package com.circus.nativenavs.data.service

import com.circus.nativenavs.data.LanguageDTO
import com.circus.nativenavs.data.LanguageListDTO
import com.circus.nativenavs.data.LoginDTO
import com.circus.nativenavs.data.LoginResponse
import com.circus.nativenavs.data.SignUpDTO
import org.intellij.lang.annotations.Language
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {

    @POST("users")
    suspend fun postSignUp(
        @Body signUpDTO: SignUpDTO
    ) : Response<Void>

    @POST("auth/login")
    suspend fun Login(
        @Body loginDTO: LoginDTO
    ) : Response<LoginResponse>

    @GET("users/authenticateEmail")
    suspend fun setEmailVerifyCode(
        @Query("email") email:String,
        @Query("authenticationCode") authenticationCode:String
    ): Response<Void>

    @POST("users/sendEmail")
    suspend fun getEmailVerifyCode(
        @Query("email") email: String
    ) : Response<Void>

    @GET("language")
    suspend fun getLanguageList():List<LanguageListDTO>

}