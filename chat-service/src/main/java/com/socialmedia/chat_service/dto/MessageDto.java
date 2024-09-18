package com.socialmedia.chat_service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record MessageDto(UUID id, String content, LocalDateTime sentAt, UUID chatId, UUID senderId, String senderUsername) {
}
