package com.nativenavs.notification.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmSendDTO {
    private String token;
    private Data data;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Data {
        private int flag;
        private String title;
        private String body;
        private String reservationId;
        private String tourId;
        private String roomId;
    }
}
