package com.circus.nativenavs.data

import java.time.LocalDateTime

data class MessageDto(
    val id: String = "",
    val roomId: Int = 0,
    val senderId: Int = 0,
    val senderNickname: String = "",
    val senderProfileImage: String = "",
    val content: String = "",
    val sendTime: LocalDateTime = LocalDateTime.now(),
    val isRead: Boolean = false
)
