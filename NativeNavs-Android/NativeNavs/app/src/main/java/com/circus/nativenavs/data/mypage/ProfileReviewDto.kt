package com.circus.nativenavs.data.mypage

import org.intellij.lang.annotations.Language

data class ProfileReviewDto(
    val rating:Int,
    val data:String,
    val content:String,
    val img:String,
    val userName:String,
    val userLanguage: String) {

}