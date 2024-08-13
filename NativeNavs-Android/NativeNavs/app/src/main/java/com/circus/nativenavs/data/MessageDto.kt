package com.circus.nativenavs.data

data class MessageDto(
    val id: String = "",
    val roomId: Int = 0,
    val senderId: Int = 0,
    val senderNickname: String = "",
    val senderProfileImage: String = "",
    val content: String = "",
    val sendTime: String = "",
    val messageChecked: Boolean = false,
    var translatedContent: String = "",
    var isTranslated: Boolean = false,
)
