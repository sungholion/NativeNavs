package com.nativenavs.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.nativenavs.notification.dto.FcmMessageDTO;
import com.nativenavs.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class FcmServiceImpl implements FcmService {

    @Autowired
    private UserRepository userRepository;

//    @Override
//    public int sendMessageTo(FcmSendDTO fcmSendDTO) throws IOException {
//        String message = makeMessage(fcmSendDTO);
//        RestTemplate restTemplate = new RestTemplate();
//
//        restTemplate.getMessageConverters()
//                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", "Bearer " + getAccessToken());
//
//        HttpEntity<String> entity = new HttpEntity<>(message, headers);
//
//        String API_URL = "https://fcm.googleapis.com/v1/projects/nativenavs/messages:send";
//        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);
//
//        return response.getStatusCode() == HttpStatus.OK ? 1 : 0;
//    }

    public int sendMessageTo(int flag, int userId, int reservationId, int tourId, int roomId) throws IOException {
        String message = sendNotification(flag, userId, reservationId, tourId, roomId);
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + getAccessToken());

        HttpEntity<String> entity = new HttpEntity<>(message, headers);

        String API_URL = "https://fcm.googleapis.com/v1/projects/nativenavs/messages:send";
        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

        return response.getStatusCode() == HttpStatus.OK ? 1 : 0;
    }

    @Override
    public String sendNotification(int flag, int userId, int reservationId, int tourId, int roomId) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();

        String sendToken = userRepository.findFcmTokenById(userId);
        System.out.println(sendToken);
        String sendFlag = String.valueOf(flag);
        String sendTitle;
        String sendMessage;
        String sendReservationId;
        String sendTourId;
        String sendRoomId;

        // flag 1 : 채팅 / 2 : 예약 신청 완료 / 3 : 투어 종료 / 4 : 투어 예정 알림
        if(flag == 1){
            sendTitle = "채팅 시작";
            sendMessage = "김싸피님과 채팅을 시작합니다";
            sendReservationId = String.valueOf(-1);
            sendTourId = String.valueOf(-1);
            sendRoomId = String.valueOf(roomId);
        }
        else if(flag == 2){
            sendTitle = "예약 신청 완료";
            sendMessage = "부산 가이드 예약 신청이 완료되었습니다";
            sendReservationId = String.valueOf(reservationId);
            sendTourId = String.valueOf(-1);
            sendRoomId = String.valueOf(-1);
        }
        else if(flag == 3){
            sendTitle = "투어 종료";
            sendMessage = "부산 가이드 투어가 종료되었습니다";
            sendReservationId = String.valueOf(reservationId);
            sendTourId = String.valueOf(-1);
            sendRoomId = String.valueOf(-1);
        }
        else if(flag == 4){
            sendTitle = "오늘은 투어 날";
            sendMessage = "오늘은 투어 날입니다. 예정 시간은 14:30분입니다";
            sendReservationId = String.valueOf(reservationId);
            sendTourId = String.valueOf(-1);
            sendRoomId = String.valueOf(-1);
        }
        else{
            return "";
        }

        FcmMessageDTO.Data data = new FcmMessageDTO.Data(
                sendFlag,
                sendTitle,
                sendMessage,
                sendReservationId,
                sendTourId,
                sendRoomId
        );

        FcmMessageDTO.Message message = FcmMessageDTO.Message.builder()
                .token(sendToken)
                .data(data)
                .build();

        FcmMessageDTO fcmMessageDTO = FcmMessageDTO.builder()
                .message(message)
                .build();


        return om.writeValueAsString(fcmMessageDTO);

    }

    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "firebase/firebase_service_key.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

//    private String makeMessage(FcmSendDTO fcmSendDTO) throws JsonProcessingException {
//        ObjectMapper om = new ObjectMapper();
//
//        // 모든 int 타입 값을 문자열로 변환합니다.
//        FcmMessageDTO.Data data = new FcmMessageDTO.Data(
//                String.valueOf(fcmSendDTO.getData().getFlag()),
//                fcmSendDTO.getData().getTitle(),
//                fcmSendDTO.getData().getBody(),
//                String.valueOf(fcmSendDTO.getData().getReservationId()),
//                String.valueOf(fcmSendDTO.getData().getTourId()),
//                String.valueOf(fcmSendDTO.getData().getRoomId())
//        );
//
//        FcmMessageDTO.Message message = FcmMessageDTO.Message.builder()
//                .token(fcmSendDTO.getToken())
//                .data(data)
//                .build();
//
//        FcmMessageDTO fcmMessageDto = FcmMessageDTO.builder()
//                .message(message)
//                .build();
//
//        return om.writeValueAsString(fcmMessageDto);
//    }
}
