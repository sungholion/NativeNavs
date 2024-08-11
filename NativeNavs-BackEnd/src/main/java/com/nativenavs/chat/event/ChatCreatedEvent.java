package com.nativenavs.chat.event;

public record ChatCreatedEvent(int roomId, String content, String sendTime) {
}