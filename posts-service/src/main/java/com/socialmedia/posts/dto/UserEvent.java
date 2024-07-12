package com.socialmedia.posts.dto;
import java.util.UUID;

public record UserEvent(String eventType, UUID userId, String username, String email) {
}