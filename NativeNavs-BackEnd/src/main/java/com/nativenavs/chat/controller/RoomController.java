package com.nativenavs.chat.controller;

import com.nativenavs.chat.config.WebSocketConfig;
import com.nativenavs.chat.dto.ChatDTO;
import com.nativenavs.chat.dto.RoomDTO;
import com.nativenavs.chat.service.ChatService;
import com.nativenavs.chat.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
@CrossOrigin("*")
@Tag(name = "chat API", description = "chat")
public class RoomController {
    // DI --------------------------------------------------------------------------------------------------------------

    private final ChatService chatService;
    private final RoomService roomService;
    private final WebSocketConfig webSocketConfig;

    // API -------------------------------------------------------------------------------------------------------------

    @Operation(summary = "채팅방 생성 API", description = "채팅방을 생성하는 API. 투어 상세보기에서 '1:1 하기' 클릭 시 채팅방이 생성된다.")
    @PostMapping("/create/{tourId}")
    public RoomDTO createRoom(@PathVariable("tourId") int tourId, @RequestHeader("Authorization") String token) {
        return roomService.createRoom(tourId,token);    // tourId로 해당 tourId를 가진 roomDTO를 생성하여 반환한다.
    }

    @Operation(summary = "채팅방 목록 API", description = "채팅방 목록을 보는 API. 현재 유저가 진행중인 채팅방 전체를 조회한다.")
    @GetMapping("/search/all")
    public ResponseEntity<?> roomList(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(roomService.myRoomList(token));   // token으로 현재 로그인중인 user의 room을 리스트로 반환한다.
    }

    // service가 chat에 있는 게 맞나? Room에 Chat을 저장해야할까? 조회만 하면될까
    @Operation(summary = "채팅방 입장 API", description = "채팅방에 입장하는 API. 해당 채팅방의 Chat 목록을 불러온다.")
    @GetMapping("enter/{roomId}")
    public List<ChatDTO> joinRoom(@PathVariable int roomId, @RequestHeader("Authorization") String token) {
        return chatService.findAllChatByRoomId(roomId,token);   // roomId로 해당 채팅방에 저장된 모든 채팅을 리스트로 반환
    }

    // 개선 필요. joinRoom과 왜 분리? token은 필요없나?
    @Operation(summary = "채팅방 조회 API", description = "채팅방 정보를 조회하는 API. 채팅 알림 클릭시 해당 채팅방을 DTO로 반환한다")
    @GetMapping("search/{roomId}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable int roomId) {
        return ResponseEntity.ok(roomService.findRoomDTOById(roomId));
    }

}
