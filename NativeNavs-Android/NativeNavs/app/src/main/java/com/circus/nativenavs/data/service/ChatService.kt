package com.circus.nativenavs.data.service

import com.circus.nativenavs.data.ChatRoomDto
import com.circus.nativenavs.data.ChatTourInfoDto
import com.circus.nativenavs.data.MessageDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatService {

    @GET("rooms/search/all")
    suspend fun getChatRoomList(): List<ChatRoomDto>

    @GET("tour/{roomId}")
    suspend fun getChatTourInfo(
        @Path(value = "roomId") roomId: Int
    ): ChatTourInfoDto

    @GET("rooms/enter/{roomId}")
    suspend fun getChatMessages(
        @Path(value = "roomId") roomId: Int
    ): List<MessageDto>

    @POST("rooms/create/{tourId}")
    suspend fun createChatRoom(
        @Path(value = "tourId") tourId: Int
    ): ChatRoomDto

//    @GET("chat/messages/{roomId}")
//    suspend fun getMessageList(
//        @Path(value = "roomId") roomId: Int
//    ): List<MessageDto>

}