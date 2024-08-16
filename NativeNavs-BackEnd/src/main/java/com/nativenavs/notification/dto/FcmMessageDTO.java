package com.nativenavs.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FcmMessageDTO {
    private Message message;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Message {
        private String token;
        private Data data;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Data {
        private String flag;
        private String title;
        private String body;
        private String reservationId;
        private String tourId;
        private String roomId;
    }
}
