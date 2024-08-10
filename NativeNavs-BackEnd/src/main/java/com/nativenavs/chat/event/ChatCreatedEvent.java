package com.nativenavs.chat.event;

import lombok.Getter;

@Getter
public record ChatCreatedEvent(int roomId, String content, String sendTime) {
}