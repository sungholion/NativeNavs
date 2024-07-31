package com.circus.nativenavs.data

data class SignUpDTO(
    val email: String,
    val password: String,
    val isNav: Boolean,
    val nickname: String,
    val userLanguage: String,
    val name: String,
    val phone: String,
    val nation: String,
    val birth : String,
    val isKorean : Boolean,
    val image : String = "",
    val device : String = ""
){

}

data class LanguageListDTO (val language : List<String>){

}