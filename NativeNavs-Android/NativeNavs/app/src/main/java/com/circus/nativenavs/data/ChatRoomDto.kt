package com.circus.nativenavs.data

data class ChatRoomDto(
    val chatId: Int,
    val tourTitle: String,
    val senderNickname: String,
    val recentMessage: String,
    val recentMessageTime: Long,
    val imgUrl: String
)
