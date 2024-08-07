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
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
@CrossOrigin("*") // 아직 고민..
public class RoomController {

    private final ChatService chatService;
    private final RoomService roomService;

    // 채팅방 참여하기
    @Tag(name = "채팅방 API", description = "채팅방 만들기 / 보기 등")
    @Operation(summary = "채팅방 참여", description = "채팅방에 참여한다")
    @GetMapping("enter/{roomId}")
    public List<ChatDTO> joinRoom(@PathVariable int roomId, @RequestHeader("Authorization") String token, Model model) {

        List<ChatDTO> chatList = chatService.findAllChatByRoomId(roomId,token);

        return chatList;
    }


    // 채팅방 등록
    @Tag(name = "채팅방 API", description = "채팅방 만들기 / 보기 등")
    @Operation(summary = "채팅방 생성", description = "채팅방을 생성한다")
    @PostMapping("/create/{tourId}")
    public RoomDTO createRoom(@PathVariable("tourId") int tourId, @RequestHeader("Authorization") String token) {
        RoomDTO newRoom = roomService.createRoom(tourId,token);
        System.out.println("Backend RoomController return roomId is : newRoom.getRoomId()");
        return newRoom;

    }

    /**
     * 나의 채팅방 리스트 보기
     */
    @Tag(name = "채팅방 API", description = "채팅방 만들기 / 보기 등")
    @Operation(summary = "나의 채팅방 목록 보기", description = "나의 채팅방 목록을 본다")
    @GetMapping("/search/all")
    public ResponseEntity<?> roomList(@RequestHeader("Authorization") String token) {
        List<RoomEntity> myRoomList = roomService.findAllRoom(token);

        return ResponseEntity.ok(myRoomList);
    }


}
