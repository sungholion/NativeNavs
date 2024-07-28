package com.circus.nativenavs.data.signup

data class SignUpDTO(
    val email: String,
    val password: String,
    val isNav: Boolean,
    val nickname: String,
    val language: List<String>,
    val name: String,
    val phone: String,
    val nation: String,
    val birth : String,
    val isKorean : Boolean
)
