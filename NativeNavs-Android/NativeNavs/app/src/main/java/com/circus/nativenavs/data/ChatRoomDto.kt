package com.circus.nativenavs.data

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
    val recentMessageTime: String
)
