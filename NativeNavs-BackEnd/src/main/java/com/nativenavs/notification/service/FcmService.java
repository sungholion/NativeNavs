package com.nativenavs.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface FcmService {

    int sendMessageTo(int flag, int userId, int reservationId, int tourId, int roomId) throws IOException;
    String sendNotification(int flag, int userId, int reservationId, int tourId, int roomId) throws JsonProcessingException;


}