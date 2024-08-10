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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.log

private const val TAG = "KrossbowChattingViewMod"

class KrossbowChattingViewModel : ViewModel() {

    private val _chatRoomList = MutableLiveData<List<ChatRoomDto>>()
    val chatRoomList: LiveData<List<ChatRoomDto>> = _chatRoomList

    private val _currentChatRoom = MutableLiveData<ChatRoomDto>()
    val currentChatRoom: LiveData<ChatRoomDto> = _currentChatRoom

    private val _chatRoomId = MutableLiveData<Int>(-1)
    val chatRoomId: LiveData<Int> = _chatRoomId

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

    fun createChatRoom(tourId: Int) {
        Log.d(TAG, "createChatRoom: 실행전")
        viewModelScope.launch {
            _currentChatRoom.value = chatRetrofit.createChatRoom(tourId)
            Log.d(TAG, "createChatRoom: ${currentChatRoom.value}")
            currentChatRoom.value?.let {
                _chatRoomId.value = it.roomId
            }
        }
    }

    fun setCurrentChatRoom(chatRoom: ChatRoomDto) {
        _currentChatRoom.value = chatRoom
        currentChatRoom.value?.let {
            _chatRoomId.value = it.roomId
        }
    }

    fun setCurrentChatRoom(roomId: Int) {
        viewModelScope.launch {
            _currentChatRoom.value = chatRetrofit.getChatRoom(roomId)
            currentChatRoom.value?.let {
                _chatRoomId.value = it.roomId
            }
        }

    }

    fun getChatRoomList() {
        viewModelScope.launch {
            Log.d(TAG, "getChatRoomList raw: ${chatRetrofit.getChatRoomList()}")
            _chatRoomList.value = chatRetrofit.getChatRoomList()
            Log.d(TAG, "getChatRoomList: ${chatRoomList.value}")
        }
    }

    fun setChatRoomId(roomId: Int) {
        _chatRoomId.value = roomId
        Log.d(TAG, "setChatRoomId: ${chatRoomId.value}")
    }

    fun getChatMessages(roomId: Int) {
        viewModelScope.launch {
            _chatMessages.value = chatRetrofit.getChatMessages(roomId)
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
                    url = "ws://i11d110.p.ssafy.io/api/ws-stomp/websocket",
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
                    destination = "/room/${chatRoomId.value}",
//                    customHeaders = mapOf(
//                        "Authorization" to "${SharedPref.accessToken}"
//                    )
                )
            )

            isConnected = true

            subscription.collect { frame ->
                Log.d(TAG, "frame observeMessages: $frame")
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
//            if (message.senderId != uiState.value!!.senderId)
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

    fun setSenderInfo(senderId: Int, senderNickname: String, senderImg: String) {
        _uiState.value?.let {
            it.senderId = senderId
            it.senderNickName = senderNickname
            it.senderImg = senderImg
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
                        destination = "/send/${chatRoomId.value}",
//                        customHeaders = mapOf(
//                            "Authorization" to "${SharedPref.accessToken}"
//                        )
                    ),
                    message
                )

                messageSent()
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
        Log.d(TAG, "message: 메세지 객체 생성")
        val tempMessage = _uiState.value?.let {
            MessageDto(
                roomId = currentChatRoom.value!!.roomId,
                senderId = it.senderId,
                senderNickname = it.senderNickName,
                senderProfileImage = it.senderImg,
                content = it.message,
                sendTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                    .toString(),
                isRead = false
            )
        } ?: MessageDto()
        Log.d(TAG, "message: $tempMessage")
        return tempMessage
    }

    fun disconnectWebSocket() {
        if (isConnected) {
            viewModelScope.launch {
                stompSession.disconnect()
                updateConnectionStatus(ConnectionStatus.CLOSED)
                isConnected = false
            }
        }

    }
}