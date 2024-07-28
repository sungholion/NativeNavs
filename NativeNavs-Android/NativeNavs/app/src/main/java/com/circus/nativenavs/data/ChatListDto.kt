package com.circus.nativenavs.data

data class ChatListDto(
    val chatId: Int,
    val tourTitle: String,
    val opponentNickname: String,
    val recentMessage: String,
    val tourRegion: String,
    val recentMessageTime: Long,
    val imgUrl: String
)
