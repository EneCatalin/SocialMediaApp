package com.socialmedia.chat_service.dto;

import java.util.List;
import java.util.UUID;

public record ChatRequestDto(List<UUID> userIds) {}
