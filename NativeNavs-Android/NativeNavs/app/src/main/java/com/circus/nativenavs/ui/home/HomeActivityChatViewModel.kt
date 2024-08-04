package com.circus.nativenavs.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.circus.nativenavs.data.MessageDto
import com.circus.nativenavs.ui.chat.ChatScreenUiState
import com.circus.nativenavs.ui.chat.ConnectionStatus
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.stomp.StompSession
import org.hildan.krossbow.stomp.conversions.convertAndSend
import org.hildan.krossbow.stomp.conversions.moshi.withMoshi
import org.hildan.krossbow.stomp.headers.StompSendHeaders
import org.hildan.krossbow.stomp.headers.StompSubscribeHeaders
import org.hildan.krossbow.websocket.okhttp.OkHttpWebSocketClient
import java.time.Duration


private const val TAG = "WebSocketChattingViewMo"

class KrossbowChattingViewModel : ViewModel() {

    private val _uiState = MutableLiveData(ChatScreenUiState())
    val uiState: LiveData<ChatScreenUiState> = _uiState

    private lateinit var stompSession: StompSession

    val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    init {
        Log.d(TAG, "init: init")
        viewModelScope.launch {
            connectWebSocket()
        }
    }

    private suspend fun connectWebSocket() {
        try {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
                .callTimeout(Duration.ofMinutes(1))
                .pingInterval(Duration.ofSeconds(10))
                .build()
            val wsClient = OkHttpWebSocketClient(okHttpClient)
            val stompClient = StompClient(wsClient)
            stompSession = stompClient.connect(
                url = "ws://주소/websocket",
                customStompConnectHeaders = mapOf(
                    "Authorization" to "토큰"
                )
            ).withMoshi(moshi)

            updateConnectionStatus(ConnectionStatus.CONNECTING)

            observeMessages()
            updateConnectionStatus(ConnectionStatus.OPENED)
        } catch (e: Exception) {
            Log.e(TAG, "WebSocket connection failed: ", e)
            updateConnectionStatus(ConnectionStatus.FAILED)
        }
    }

    private fun updateConnectionStatus(connectionStatus: ConnectionStatus) {
        _uiState.postValue(_uiState.value?.copy(connectionStatus = connectionStatus))
    }

    private suspend fun observeMessages() {
        try {
            val subscription = stompSession.subscribe(
                StompSubscribeHeaders(
                    destination = "룸id 관련 url?",
                    customHeaders = mapOf(
                        "Authorization" to "토큰"
                    )
                )
            )

            subscription.collect { frame ->
                val newMessage = moshi.adapter(MessageDto::class.java).fromJson(frame.bodyAsText)
                newMessage?.let {
                    handleOnMessageReceived(newMessage)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Message observation failed: ", e)
        }
    }

    private fun handleOnMessageReceived(message: MessageDto) {
        Log.d(TAG, "handleOnMessageReceived: $message")
        try {
            addMessage(message)
        } catch (e: Exception) {
            Log.e(TAG, "handleOnMessageReceived: ", e)
        }
    }

    fun setMessage(content: String) {
        _uiState.value?.let {
            it.message = content
        }
    }

    fun setUserId(userId: Int) {
        _uiState.value?.let {
            it.userId = userId
        }
    }

    fun setMessages(list: List<MessageDto>) {
        _uiState.value?.let { currentState ->
            _uiState.postValue(currentState.copy(messages = list))
        }
    }

    private fun addMessage(message: MessageDto) {
        Log.d(TAG, "addMessage: $message")
        val messages = uiState.value?.messages?.toMutableList()
        messages?.add(message)
        _uiState.postValue(messages?.let { _uiState.value?.copy(messages = it) })
    }

    fun sendMessage(messageSent: () -> Unit) {
        val message = message()
        if (message.content.isEmpty()) return

        viewModelScope.launch {
            try {
                stompSession.withMoshi(moshi).convertAndSend(
                    StompSendHeaders(
                        destination = "/app/chat",
                        customHeaders = mapOf(
                            "Authorization" to "토큰"
                        )
                    ),
                    message
                )
                messageSent()
                addMessage(message)
                clearMessage()
            } catch (e: Exception) {
                Log.e(TAG, "Message sending failed: ", e)
            }
        }
    }

    private fun clearMessage() {
        viewModelScope.launch {
            delay(50)
            _uiState.postValue(_uiState.value?.copy(message = ""))
        }
    }

    private fun message(): MessageDto {
        return _uiState.value?.let {
            MessageDto(
                1, it.userId, "이현진",
                "", it.message,
                System.currentTimeMillis(), 1
            )
        } ?: MessageDto(
            0, 0, "몰라",
            "", "못 읽어요", System.currentTimeMillis(), 1
        )
    }
}
