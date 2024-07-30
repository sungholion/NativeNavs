package com.circus.nativenavs.data.service

import com.circus.nativenavs.data.LoginDTO
import com.circus.nativenavs.data.LoginResponse
import com.circus.nativenavs.data.SignUpDTO
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

    @GET("users/authenticate")
    suspend fun setEmailVerifyCode(
        @Query("email") email:String,
        @Query("authenticationCode") authenticationCode:String
    ): Response<Void>

    @POST("users/authenticate")
    suspend fun getEmailVerifyCode(
        @Body email: String
    ) : Response<Void>

}