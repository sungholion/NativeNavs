package com.circus.nativenavs.data.service

import com.circus.nativenavs.data.CategoryDto
import com.circus.nativenavs.data.LanguageServerDto
import com.circus.nativenavs.data.LoginDto
import com.circus.nativenavs.data.LoginResponse
import com.circus.nativenavs.data.ProfileUserDto
import com.circus.nativenavs.data.ProfileUserReviewDto
import com.circus.nativenavs.data.RefreshResponse
import com.circus.nativenavs.data.SignUpDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @Multipart
    @POST("users")
    suspend fun postSignUp(
        @Part userDto: MultipartBody.Part,
        @Part image: MultipartBody.Part
    ): Response<Void>

    @POST("auth/login")
    suspend fun Login(
        @Body loginDTO: LoginDto
    ): Response<LoginResponse>

    @POST("auth/refresh")
    suspend fun refresh(
        @Body refresh: Map<String, String>
    ): Response<RefreshResponse>

    @GET("users/authenticateEmail")
    suspend fun setEmailVerifyCode(
        @Query("email") email: String,
        @Query("authenticationCode") authenticationCode: String
    ): Response<Void>

    @POST("users/sendEmail")
    suspend fun getEmailVerifyCode(
        @Query("email") email: String
    ): Response<Void>

    @GET("language")
    suspend fun getLanguageList(): LanguageServerDto

    @GET("users/checkDuplicated/nickname/{nickname}")
    suspend fun isDupliNick(
        @Path(value = "nickname") nickname: String
    ): Response<Void>

    @GET("users/search/id/{id}")
    suspend fun searchUser(
        @Path(value = "id") userId: Int
    ): ProfileUserDto

    @Multipart
    @PUT("users")
    suspend fun updateUser(
        @Part userDto: MultipartBody.Part,
        @Part image: MultipartBody.Part?
    ): Response<Void>

    @DELETE("users/delete")
    suspend fun deleteUser(): Response<Void>

    @GET("tours/category")
    suspend fun getCategory(): List<CategoryDto>

    @GET("reviews/guide/{guideId}")
    suspend fun getNavReview(
        @Path(value = "guideId") guideId : Int
    ) : ProfileUserReviewDto

    @GET("reviews/user/{travId}")
    suspend fun getTravReview(
        @Path(value = "travId") guideId : Int
    ) : ProfileUserReviewDto

    @GET("users/fcm")
    suspend fun postFcmToken(
        @Body fcmToken: Map<String, String>
    ): Response<Void>
}