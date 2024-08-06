package com.circus.nativenavs.data

data class ChatRoomDto(
    val roomId: Int,
    val travId: Int,
    val navId: Int,
    val travNickname: String,
    val navNickname: String,
    val tourTitle: String,
    val imgUrl: String,
    val tourRegion: String,
    val recentMessage: String,
    val recentMessageTime: Long
)
