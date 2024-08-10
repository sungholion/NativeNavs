package com.nativenavs.chat.service;

import com.nativenavs.auth.jwt.JwtTokenProvider;
import com.nativenavs.chat.dto.RoomDTO;
import com.nativenavs.chat.entity.RoomEntity;
import com.nativenavs.chat.event.ChatCreatedEvent;
import com.nativenavs.chat.repository.RoomRepository;
import com.nativenavs.tour.dto.TourDTO;
import com.nativenavs.tour.service.TourService;
import com.nativenavs.user.dto.UserDTO;
import com.nativenavs.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RoomService {
    // DI --------------------------------------------------------------------------------------------------------------

    private final UserService userService;
    private final RoomRepository roomRepository;
    private final ChatService chatService;
    private final TourService tourService;

    // Method ----------------------------------------------------------------------------------------------------------

    public RoomDTO createRoom(int tourId, String token) {
        TourDTO tourDTO = tourService.findTourByTourId(tourId);   // 수환이가만들어준 이름으로 바꾸기

        if(tourDTO != null) {
            UserDTO navUserDTO = tourDTO.getUser(); // 투어 작성자(Nav) DTO

            String jwtToken = token.replace("Bearer ", ""); // Token
            String email = JwtTokenProvider.getEmailFromToken(jwtToken);    // Token에서 email 추출

            if(email == null){  // 현재 로그인한 회원이 인증/인가에 문제가 있다면?
                return null;
            }

            UserDTO travUserDTO = userService.searchByEmail(email); // 투어 이용자(Trav) DTO

            RoomEntity newRoom = RoomEntity.createRoom(tourDTO.getId(), tourDTO.getTitle(), tourDTO.getThumbnailImage(), tourDTO.getLocation(), travUserDTO.getId(), travUserDTO.getNickname(), travUserDTO.getIsNav(), navUserDTO.getId(), navUserDTO.getNickname(), navUserDTO.getIsNav());
            roomRepository.save(newRoom);

            RoomDTO newRoomDTO = RoomDTO.toRoomDTO(newRoom);
            chatService.createChat(newRoomDTO.getRoomId(), travUserDTO.getId(), travUserDTO.getNickname(), travUserDTO.getImage(), "문의 신청합니다.", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

            return newRoomDTO;
        }
        else{
            throw new NoSuchElementException(); // 해당 tour가 없다면 예외처리
        }
    }

    public List<RoomEntity> myRoomList(String token) { //findAllRoom -> myRoomList
        String jwtToken = token.replace("Bearer ", ""); // "Bearer " 부분 제거
        String email = JwtTokenProvider.getEmailFromToken(jwtToken);
        UserDTO UserDTO = userService.searchByEmail(email); // token으로부터 현재 로그인한 userDTO 찾기

        if(UserDTO.getIsNav()){ // 가이드라면
            return roomRepository.findAllByReceiverId(UserDTO.getId());
        } else{ // 여행자라면
            return roomRepository.findAllBySenderId(UserDTO.getId());
        }
    }

    public RoomDTO findRoomDTOById(int roomId) {
        RoomEntity roomEntity = roomRepository.findById(roomId)
                .orElseThrow(() -> new NoSuchElementException("Room not found with id: " + roomId));
        return RoomDTO.toRoomDTO(roomEntity);
    }

    public void updateRecentMessageInfo(int roomId, String content, String sendTime){   // 최신 채팅 목록에서 확인하기 위함
        RoomEntity room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room ID: " + roomId)); // 방 찾기 -> 없는 방일 경우 예외처리

        room.setRecentMessageContent(content);
        room.setRecentMessageTime(sendTime);
        roomRepository.save(room);
    }

    @EventListener
    public void handleChatCreatedEvent(ChatCreatedEvent event) {
        RoomEntity room = roomRepository.findById(event.roomId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid room ID: " + event.roomId()));

        room.setRecentMessageContent(event.content());
        room.setRecentMessageTime(event.sendTime());
        roomRepository.save(room);
    }

}
