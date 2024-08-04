package com.circus.nativenavs.data.service

import com.circus.nativenavs.data.ChatRoomDto
import com.circus.nativenavs.data.ChatTourInfoDto
import com.circus.nativenavs.data.MessageDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ChatService {

    @GET("chat/list/{userId}")
    suspend fun getChatRoomList(
        @Path(value = "userId") userId: Int
    ): List<ChatRoomDto>

    @GET("chat/tour/{roomId}")
    suspend fun getChatTourInfo(
        @Path(value = "roomId") roomId: Int
    ): ChatTourInfoDto

    @GET("chat/messages/{roomId}")
    suspend fun getMessageList(
        @Path(value = "roomId") roomId: Int
    ): List<MessageDto>

}