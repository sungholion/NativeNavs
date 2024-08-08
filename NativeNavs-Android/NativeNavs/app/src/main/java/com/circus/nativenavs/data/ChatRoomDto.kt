package com.circus.nativenavs.data

import java.time.LocalDateTime

data class ChatRoomDto(
    val roomId: Int,
    val senderId: Int,
    val receiverId: Int,
    val senderNickname: String,
    val receiverNickname: String,
    val senderIsNav: Boolean,
    val receiverIsNav: Boolean,
    val tourId: Int,
    val tourTitle: String,
    val tourImgUrl: String,
    val tourRegion: String,
    val recentMessage: String,
    val recentMessageTime: LocalDateTime
)
