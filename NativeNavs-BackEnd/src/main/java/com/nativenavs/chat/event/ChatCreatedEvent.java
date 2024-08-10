package com.nativenavs.chat.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ChatCreatedEvent {
    private final int roomId;
    private final String content;
    private final String sendTime;
}