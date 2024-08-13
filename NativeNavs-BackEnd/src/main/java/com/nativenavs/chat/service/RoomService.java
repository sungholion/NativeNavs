package com.nativenavs.chat.service;

import com.nativenavs.auth.jwt.JwtTokenProvider;
import com.nativenavs.chat.dto.ChatDTO;
import com.nativenavs.chat.dto.RoomDTO;
import com.nativenavs.chat.entity.ChatEntity;
import com.nativenavs.chat.entity.RoomEntity;
import com.nativenavs.chat.event.ChatCreatedEvent;
import com.nativenavs.chat.repository.RoomRepository;
import com.nativenavs.tour.dto.TourDTO;
import com.nativenavs.tour.service.TourService;
import com.nativenavs.user.dto.UserDTO;
import com.nativenavs.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
    // DI --------------------------------------------------------------------------------------------------------------

    private final UserService userService;
    private final RoomRepository roomRepository;
    private final ChatService chatService;
    private final TourService tourService;
    private final ApplicationEventPublisher eventPublisher;

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


            // 중복 방지: 이미 동일한 투어에 대해 사용자가 채팅방을 가지고 있는지 확인
            RoomEntity existingRoom = roomRepository.findByTourIdAndSenderId(tourId, travUserDTO.getId());
            if (existingRoom != null) {
                // 이미 존재하는 방의 RoomDTO 반환
                System.out.println("중복!!!!!!!!!!!!!!!!!");
                return RoomDTO.toRoomDTO(existingRoom);
            }


            RoomEntity newRoom = RoomEntity.createRoom(tourDTO.getId(), tourDTO.getTitle(), tourDTO.getThumbnailImage(), tourDTO.getLocation(), travUserDTO.getId(), travUserDTO.getNickname(), travUserDTO.getIsNav(), navUserDTO.getId(), navUserDTO.getNickname(), navUserDTO.getIsNav());
            roomRepository.save(newRoom);

            RoomDTO newRoomDTO = RoomDTO.toRoomDTO(newRoom);

            ChatEntity questionChat = chatService.createChat(newRoomDTO.getRoomId(), travUserDTO.getId(), travUserDTO.getNickname(), travUserDTO.getImage(), "문의 신청합니다", false, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            ChatDTO questionChatDTO = ChatDTO.toChatDTO(questionChat);

            eventPublisher.publishEvent(new ChatCreatedEvent(newRoom.getRoomId(), questionChatDTO.getContent(), questionChatDTO.getSendTime()));
            System.out.println("createRoom : 문의 신청합니다!!!!!!!!!!!!!" + questionChatDTO.getMessageChecked());

            return newRoomDTO;
        }
        else{
            throw new NoSuchElementException(); // 해당 tour가 없다면 예외처리
        }
    }

    public List<RoomDTO> myRoomList(String token) { //findAllRoom -> myRoomList
        String jwtToken = token.replace("Bearer ", ""); // "Bearer " 부분 제거
        String email = JwtTokenProvider.getEmailFromToken(jwtToken);
        UserDTO userDTO = userService.searchByEmail(email); // token으로부터 현재 로그인한 userDTO 찾기

        List<RoomEntity> roomEntities;

        if (userDTO.getIsNav()) { // 가이드라면
            roomEntities = roomRepository.findAllByReceiverId(userDTO.getId());
        } else { // 여행자라면
            roomEntities = roomRepository.findAllBySenderId(userDTO.getId());
        }

        // 최근 메시지 시간(recentMessageTime)으로 정렬 (최신순)
        return roomEntities.stream()
                .sorted(Comparator.comparing(RoomEntity::getRecentMessageTime, Comparator.nullsLast(Comparator.reverseOrder())))
                .map(RoomDTO::toRoomDTO)
                .collect(Collectors.toList());


    }

    public RoomDTO findRoomDTOById(int roomId) {
        RoomEntity roomEntity = roomRepository.findById(roomId)
                .orElseThrow(() -> new NoSuchElementException("Room not found with id: " + roomId));
        return RoomDTO.toRoomDTO(roomEntity);
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
