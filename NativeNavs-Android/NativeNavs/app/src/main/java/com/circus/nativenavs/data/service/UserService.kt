package com.circus.nativenavs.data.service

import com.circus.nativenavs.data.LanguageServerDto
import com.circus.nativenavs.data.LoginDto
import com.circus.nativenavs.data.LoginResponse
import com.circus.nativenavs.data.ProfileUserDto
import com.circus.nativenavs.data.SignUpDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @POST("users")
    suspend fun postSignUp(
        @Body signUpDTO: SignUpDto
    ) : Response<Void>

    @POST("auth/login")
    suspend fun Login(
        @Body loginDTO: LoginDto
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
    suspend fun getLanguageList():LanguageServerDto

    @GET("users/checkDuplicated/nickname/{nickname}")
    suspend fun isDupliNick(
        @Path(value = "nickname") nickname : String
    ):Response<Void>

    @GET("users/search/id/{id}")
    suspend fun searchUser(
        @Path(value = "id") userId: Int
    ): ProfileUserDto
}