package com.nativenavs.chat.controller;

import com.nativenavs.chat.dto.ChatDTO;
import com.nativenavs.chat.dto.RoomDTO;
import com.nativenavs.chat.entity.RoomEntity;
import com.nativenavs.chat.service.ChatService;
import com.nativenavs.chat.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin("*")
@Tag(name = "chat API", description = "chat")
public class RoomController {

    private final ChatService chatService;
    private final RoomService roomService;

    RoomController(ChatService chatService, RoomService roomService) {
        this.chatService = chatService;
        this.roomService = roomService;
    }

    // 채팅방 참여하기
    @Operation(summary = "채팅방 입장 API", description = "채팅방에 입장하는 API")
    @GetMapping("enter/{roomId}")
    public List<ChatDTO> joinRoom(@PathVariable int roomId, @RequestHeader("Authorization") String token, Model model) {

        List<ChatDTO> chatList = chatService.findAllChatByRoomId(roomId,token);

        return chatList;
    }


    // 채팅방 등록
    @Operation(summary = "채팅방 생성 API", description = "채팅방을 생성하는 API")
    @PostMapping("/create/{tourId}")
    public RoomDTO createRoom(@PathVariable("tourId") int tourId, @RequestHeader("Authorization") String token) {
        RoomDTO newRoom = roomService.createRoom(tourId,token);
        System.out.println("Backend RoomController return roomId is : " + newRoom.getRoomId());
        return newRoom;

    }

    @Operation(summary = "채팅방 목록 API", description = "채팅방 목록을 보는 API")
    @GetMapping("/search/all")
    public ResponseEntity<?> roomList(@RequestHeader("Authorization") String token) {
        List<RoomEntity> myRoomList = roomService.findAllRoom(token);

        return ResponseEntity.ok(myRoomList);
    }


    @Operation(summary = "채팅방 조회 API", description = "채팅방 정보를 조회하는 API")
    @GetMapping("search/{roomId}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable int roomId) {
        RoomDTO roomDTO = roomService.findRoomDTOById(roomId);
        return ResponseEntity.ok(roomDTO);
    }

}
