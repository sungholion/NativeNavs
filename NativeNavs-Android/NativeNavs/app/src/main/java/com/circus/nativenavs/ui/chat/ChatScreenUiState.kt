package com.circus.nativenavs.ui.chat

import com.circus.nativenavs.data.MessageDto

data class ChatScreenUiState(
    var messages: List<MessageDto> = listOf(),
    var userId: Int = -1,
    var message: String = "",
    val connectionStatus: ConnectionStatus = ConnectionStatus.NOT_STARTED
)