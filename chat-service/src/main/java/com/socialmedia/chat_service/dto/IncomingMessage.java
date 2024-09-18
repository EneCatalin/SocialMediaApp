package com.socialmedia.chat_service.dto;

import java.util.UUID;

public record IncomingMessage(UUID chatId, UUID senderId, String content) {
}
