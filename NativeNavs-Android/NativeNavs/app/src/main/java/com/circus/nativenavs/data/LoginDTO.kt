package com.circus.nativenavs.data

data class LoginDTO(
    val email: String,
    val password : String,
    val device : String
){

}

data class LoginResponse(
    val message: String,
    val accessToken: String,
    val refreshToken: String,
    val id: Int,
    val isNav : Boolean
)
