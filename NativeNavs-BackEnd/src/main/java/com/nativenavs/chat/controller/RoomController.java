package com.nativenavs.chat.controller;

import com.nativenavs.chat.dto.ChatDTO;
import com.nativenavs.chat.entity.RoomEntity;
import com.nativenavs.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class RoomController {

    private final ChatService chatService;

    // 채팅방 참여하기
    @GetMapping("/{roomId}")public String joinRoom(@PathVariable int roomId, Model model) {
        List<ChatDTO> chatList = chatService.findAllChatByRoomId(roomId).stream()
                .map(chatEntity -> ChatDTO.builder()
                        .roomId(chatEntity.getRoomId())
                        .senderId(chatEntity.getSenderId())
                        .senderNickname(chatEntity.getSenderNickname())
                        .senderProfileImage(chatEntity.getSenderProfileImage())
                        .content(chatEntity.getContent())
                        .sendTime(chatEntity.getSendTime())
                        .isRead(chatEntity.isRead())
                        .build())
                .collect(Collectors.toList());

        model.addAttribute("roomId", roomId);
        model.addAttribute("chatList", chatList);
        return "chat/room";
    }

    /**
     * 채팅방 등록
     * @param form
     */
    @PostMapping("/room")
    public String createRoom(RoomForm form) {
        chatService.createRoom(form.getName());
        return "redirect:/roomList";
    }

    /**
     * 채팅방 리스트 보기
     */
    @GetMapping("/roomList")
    public String roomList(Model model) {
        List<RoomEntity> roomList = chatService.findAllRoom();
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
