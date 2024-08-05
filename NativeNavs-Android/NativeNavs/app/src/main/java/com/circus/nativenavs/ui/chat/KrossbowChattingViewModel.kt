package com.circus.nativenavs.ui.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.circus.nativenavs.config.ApplicationClass
import com.circus.nativenavs.data.ChatRoomDto
import com.circus.nativenavs.data.ChatTourInfoDto
import com.circus.nativenavs.data.MessageDto
import com.circus.nativenavs.data.service.ChatService
import com.circus.nativenavs.util.SharedPref
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
import org.hildan.krossbow.stomp.use
import org.hildan.krossbow.websocket.okhttp.OkHttpWebSocketClient
import java.time.Duration

private const val TAG = "KrossbowChattingViewMod"

class KrossbowChattingViewModel : ViewModel() {

    private val _chatRoomList = MutableLiveData<List<ChatRoomDto>>()
    val chatRoomList: LiveData<List<ChatRoomDto>> = _chatRoomList

    private val _chatRoomId = MutableLiveData<Int>()
    val chatRoomId: LiveData<Int> = _chatRoomId

    private val _chatTourInfo = MutableLiveData<ChatTourInfoDto>()
    val chatTourInfo: LiveData<ChatTourInfoDto> = _chatTourInfo

    private val _chatMessages = MutableLiveData<List<MessageDto>>()
    val chatMessages: LiveData<List<MessageDto>> = _chatMessages

    private val chatRetrofit = ApplicationClass.retrofit.create(ChatService::class.java)

    private val _uiState = MutableLiveData(ChatScreenUiState())
    val uiState: LiveData<ChatScreenUiState> = _uiState

    private lateinit var stompSession: StompSession
    private var isConnected = false

    private val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    fun getChatRoomList(userId: Int) {
        viewModelScope.launch {
            _chatRoomList.value = chatRetrofit.getChatRoomList(userId)
        }
    }

    fun setChatRoomId(roomId: Int) {
        _chatRoomId.value = roomId
    }

    fun getChatTourInfo(roomId: Int) {
        viewModelScope.launch {
            _chatTourInfo.value = chatRetrofit.getChatTourInfo(roomId)
        }
    }

    fun getChatMessages(roomId: Int) {
        viewModelScope.launch {
            _chatMessages.value = chatRetrofit.getMessageList(roomId)
            _chatMessages.value?.let {
                setMessages(it)
            }

        }
    }

    fun connectWebSocket() {
        viewModelScope.launch {
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
                    url = "ws://14.46.141.241/ws-stomp",
//                    customStompConnectHeaders = mapOf(
//                        "Authorization" to "${SharedPref.accessToken}"
//                    ),
                ).withMoshi(moshi)
                updateConnectionStatus(ConnectionStatus.CONNECTING)

                observeMessages()
                updateConnectionStatus(ConnectionStatus.OPENED)
            } catch (e: Exception) {
                Log.e(TAG, "WebSocket connection failed: ", e)
                updateConnectionStatus(ConnectionStatus.FAILED)
            }
        }
    }

    private fun updateConnectionStatus(connectionStatus: ConnectionStatus) {
        _uiState.postValue(_uiState.value?.copy(connectionStatus = connectionStatus))
    }

    private suspend fun observeMessages() {
        try {
            val subscription = stompSession.subscribe(
                StompSubscribeHeaders(
                    destination = "/chat/$chatRoomId",
//                    customHeaders = mapOf(
//                        "Authorization" to "${SharedPref.accessToken}"
//                    )
                )
            )

            isConnected = true

            subscription.collect { frame ->
                val newMessage = moshi.adapter(MessageDto::class.java).fromJson(frame.bodyAsText)
                newMessage?.let {
                    handleOnMessageReceived(newMessage)
                }
            }
        } catch (e: Exception) {
            isConnected = false
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
                        destination = "/chat/$chatRoomId",
//                        customHeaders = mapOf(
//                            "Authorization" to "${SharedPref.accessToken}"
//                        )
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

    fun disconnectWebSocket() {
        if (isConnected) {
            viewModelScope.launch {
                stompSession.disconnect()
            }
        }

    }
}