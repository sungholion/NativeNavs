package com.nativenavs.chat.controller;

import com.nativenavs.chat.dto.ChatDTO;
import com.nativenavs.chat.dto.RoomDTO;
import com.nativenavs.chat.service.ChatService;
import com.nativenavs.chat.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "채팅방 API", description = "채팅방 생성 / 채팅방 목록 조회 / 채팅방 입장 / 특정 채팅방 정보 조회")
public class RoomController {

    // DI --------------------------------------------------------------------------------------------------------------

    private final ChatService chatService;
    private final RoomService roomService;

    // API -------------------------------------------------------------------------------------------------------------

    @Operation(summary = "채팅방 생성 API", description = "tourId, token을 입력하여 채팅방 생성")
    @PostMapping("/create/{tourId}")
    public RoomDTO createRoom(@PathVariable("tourId") int tourId, @RequestHeader("Authorization") String token) {
        log.info("채팅방 생성 요청: tourId={}, token={}", tourId, token);

        RoomDTO roomDTO = roomService.createRoom(tourId, token);

        log.info("채팅방 생성 성공: roomId={}", roomDTO.getRoomId());

        return roomDTO;
    }

    @Operation(summary = "채팅방 목록 조회 API", description = "token으로 로그인한 회원의 채팅방 목록을 조회")
    @GetMapping("/search/all")
    public ResponseEntity<?> roomList(@RequestHeader("Authorization") String token) {
        log.info("채팅방 목록 조회 요청: token={}", token);

        List<RoomDTO> roomList = roomService.myRoomList(token);

        log.info("채팅방 목록 조회 성공: {}개의 채팅방이 조회됨", roomList.size());

        return ResponseEntity.ok(roomList);
    }

    @Operation(summary = "채팅방 입장 API", description = "roomId에 해당하는 채팅방의 채팅 List를 조회")
    @GetMapping("enter/{roomId}")
    public List<ChatDTO> joinRoom(@PathVariable int roomId, @RequestHeader("Authorization") String token) {
        log.info("채팅방 입장 요청: roomId={}, token={}", roomId, token);

        List<ChatDTO> chatList = chatService.findAllChatByRoomId(roomId, token);

        log.info("채팅방 입장 성공: roomId={}, {}개의 메시지가 조회됨", roomId, chatList.size());

        return chatList;
    }

    @Operation(summary = "특정 채팅방 정보 조회 API", description = "특정 채팅방 정보를 조회")
    @GetMapping("search/{roomId}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable int roomId) {
        log.info("특정 채팅방 정보 조회 요청: roomId={}", roomId);

        RoomDTO roomDTO = roomService.findRoomDTOById(roomId);

        log.info("특정 채팅방 정보 조회 성공: roomId={}", roomDTO.getRoomId());

        return ResponseEntity.ok(roomDTO);
    }
}

/*
    리팩토링

 */
