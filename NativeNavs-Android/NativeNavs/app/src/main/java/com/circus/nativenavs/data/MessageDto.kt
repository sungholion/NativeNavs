package com.circus.nativenavs.data

data class MessageDto(
    val messageId: Long,
    val senderId: Int,
    val senderNickname: String,
    val senderImgUrl: String,
    val content: String,
    val sendTime: Long,
    val readCount: Int = 1
)
