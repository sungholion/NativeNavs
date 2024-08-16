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


    private final UserService userService;
    private final RoomRepository roomRepository;
    private final ChatService chatService;
    private final TourService tourService;
    private final ApplicationEventPublisher eventPublisher;



    public RoomDTO createRoom(int tourId, String token) {
        TourDTO tourDTO = tourService.findTourByTourId(tourId);

        if(tourDTO != null) {
            UserDTO navUserDTO = tourDTO.getUser();

            String jwtToken = token.replace("Bearer ", "");
            String email = JwtTokenProvider.getEmailFromToken(jwtToken);

            if(email == null){
                return null;
            }

            UserDTO travUserDTO = userService.searchByEmail(email);


            RoomEntity existingRoom = roomRepository.findByTourIdAndSenderId(tourId, travUserDTO.getId());
            if (existingRoom != null) {
                return RoomDTO.toRoomDTO(existingRoom);
            }



            RoomEntity newRoom = RoomEntity.createRoom(tourDTO.getId(), tourDTO.getTitle(), tourDTO.getThumbnailImage(), tourDTO.getLocation(), travUserDTO.getId(), travUserDTO.getNickname(), travUserDTO.getIsNav(), navUserDTO.getId(), navUserDTO.getNickname(), navUserDTO.getIsNav());
            roomRepository.save(newRoom);

            RoomDTO newRoomDTO = RoomDTO.toRoomDTO(newRoom);

            ChatEntity questionChat = chatService.createChat(newRoomDTO.getRoomId(), travUserDTO.getId(), travUserDTO.getNickname(), travUserDTO.getImage(), "I would like to inquire about " + tourDTO.getTitle(), false, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            ChatDTO questionChatDTO = ChatDTO.toChatDTO(questionChat);

            eventPublisher.publishEvent(new ChatCreatedEvent(newRoom.getRoomId(), questionChatDTO.getContent(), questionChatDTO.getSendTime()));

            return newRoomDTO;
        }
        else{
            throw new NoSuchElementException();
        }
    }

    public List<RoomDTO> myRoomList(String token) {
        String jwtToken = token.replace("Bearer ", "");
        String email = JwtTokenProvider.getEmailFromToken(jwtToken);
        UserDTO userDTO = userService.searchByEmail(email);

        List<RoomEntity> roomEntities;

        if (userDTO.getIsNav()) {
            roomEntities = roomRepository.findAllByReceiverId(userDTO.getId());
        } else {
            roomEntities = roomRepository.findAllBySenderId(userDTO.getId());
        }


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
