package com.circus.nativenavs.data

data class ChatTourInfoDto(
    val roomId: Int,
    val tourTitle: String,
    val tourRegion: String,
    val imgUrl: String,
    val senderNickname: String
)