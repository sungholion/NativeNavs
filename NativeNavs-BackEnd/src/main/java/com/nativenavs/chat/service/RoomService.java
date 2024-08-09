package com.nativenavs.chat.service;

import com.nativenavs.auth.jwt.JwtTokenProvider;
import com.nativenavs.chat.dto.RoomDTO;
import com.nativenavs.chat.entity.RoomEntity;
import com.nativenavs.chat.repository.RoomRepository;
import com.nativenavs.tour.dto.TourDTO;
import com.nativenavs.tour.entity.TourEntity;
import com.nativenavs.tour.repository.TourRepository;
import com.nativenavs.user.dto.UserDTO;
import com.nativenavs.user.entity.UserEntity;
import com.nativenavs.user.repository.UserRepository;
import com.nativenavs.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final UserService userService;

    private final RoomRepository roomRepository;

    private final TourRepository tourRepository;

    private final UserRepository userRepository;
    private final ChatService chatService;

    /**
     * 모든 채팅방 찾기
     */
    public List<RoomEntity> findAllRoom(String token) {
        String jwtToken = token.replace("Bearer ", ""); // "Bearer " 부분 제거
        String email = JwtTokenProvider.getEmailFromToken(jwtToken);
        UserDTO UserDTO = userService.searchByEmail(email);
        int userId = UserDTO.getId();
        boolean isNav = UserDTO.getIsNav();

        if(isNav){ // 가이드라면
            return roomRepository.findAllByReceiverId(userId);
        } else{ // 여행자라면
            return roomRepository.findAllBySenderId(userId);
        }

    }

    /**
     * 특정 채팅방 찾기
     * @param id room_idv
     */
    public RoomEntity findRoomById(int id) {
        return roomRepository.findById(id).orElseThrow();
    }

    // 채팅방 만들기
    public RoomDTO createRoom(int tourId, String token) {
        Optional<TourEntity> optionalTourEntity = tourRepository.findById(tourId);
        if (optionalTourEntity.isPresent()) {
            TourEntity tourEntity = optionalTourEntity.get();

            // 투어 작성자
            UserEntity navUserEntity = tourEntity.getUser();
            UserDTO navUserDTO = UserDTO.toUserDTO(navUserEntity);

            // 여행자 (방만들기 신청하는 사람 sender)
            String jwtToken = token.replace("Bearer ", ""); // "Bearer " 부분 제거
            String email = JwtTokenProvider.getEmailFromToken(jwtToken);

            UserDTO travUserDTO = userService.searchByEmail(email);

            TourDTO tourDTO = TourDTO.toTourDTO(tourEntity);

            RoomEntity newRoom = RoomEntity.createRoom(tourId, tourDTO.getTitle(), tourDTO.getThumbnailImage(), tourDTO.getLocation(), travUserDTO.getId(), travUserDTO.getNickname(), travUserDTO.getIsNav(), navUserDTO.getId(), navUserDTO.getNickname(), navUserDTO.getIsNav());
            roomRepository.save(newRoom);

            RoomDTO newRoomDTO = RoomDTO.toRoomDTO(newRoom);
            chatService.createChat(newRoomDTO.getRoomId(), travUserDTO.getId(), travUserDTO.getNickname(), travUserDTO.getImage(), "문의 신청합니다.", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

            return newRoomDTO;

        } else {
            return null;
        }

    }


    public RoomDTO findRoomDTOById(int roomId) {
        RoomEntity roomEntity = roomRepository.findById(roomId)
                .orElseThrow(() -> new NoSuchElementException("Room not found with id: " + roomId));
        return RoomDTO.toRoomDTO(roomEntity);
    }


}
