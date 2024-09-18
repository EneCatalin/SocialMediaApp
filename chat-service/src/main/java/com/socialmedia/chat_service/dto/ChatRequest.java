package com.socialmedia.chat_service.dto;

import java.util.UUID;

public record ChatRequest(UUID user1Id, UUID user2Id) {
}
