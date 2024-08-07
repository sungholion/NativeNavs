package com.nativenavs.chat.controller;

import com.nativenavs.chat.dto.ChatDTO;
import com.nativenavs.chat.entity.RoomEntity;
import com.nativenavs.chat.service.ChatService;
import com.nativenavs.chat.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RoomController {

    private final ChatService chatService;
    private final RoomService roomService;

    // 채팅방 참여하기
    @GetMapping("/{roomId}")
    public String joinRoom(@PathVariable int roomId, Model model) {
        List<ChatDTO> chatList = chatService.findAllChatByRoomId(roomId);
        model.addAttribute("roomId", roomId);
        model.addAttribute("chatList", chatList);
        return "chat/room";
    }

    // 채팅방 등록
    @Tag(name = "채팅방 API", description = "채팅방 만들기 / 보기 등")
    @Operation(summary = "email 중복 체크 API", description = "email 중복 체크를 한다")
    @PostMapping("/room")
    public String createRoom(int tourId, int senderId, String senderNickname, boolean senderIsNav, int receiverId, String receiverNickname, boolean receiverIsNav) {
        roomService.createRoom(tourId, senderId, senderNickname, senderIsNav, receiverId, receiverNickname, receiverIsNav);
        return "redirect:/roomList";
    }

    /**
     * 채팅방 리스트 보기
     */
    @GetMapping("/roomList")
    public String roomList(Model model) {
        List<RoomEntity> roomList = roomService.findAllRoom();
        model.addAttribute("roomList", roomList);
        return "chat/roomList";
    }

    /**
     * 방만들기 폼
     */
    @GetMapping("/roomForm")
    public String roomForm() {
        return "chat/roomForm";
    }

}
