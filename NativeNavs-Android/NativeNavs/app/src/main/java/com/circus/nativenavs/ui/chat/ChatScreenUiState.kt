package com.circus.nativenavs.ui.chat

import com.circus.nativenavs.data.MessageDto

data class ChatScreenUiState(
    var messages: List<MessageDto> = listOf(),
    var senderId: Int = -1,
    var senderNickName: String = "",
    var senderImg: String = "",
    var message: String = "",
    val connectionStatus: ConnectionStatus = ConnectionStatus.NOT_STARTED
)