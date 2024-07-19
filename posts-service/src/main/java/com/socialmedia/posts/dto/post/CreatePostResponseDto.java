package com.socialmedia.posts.dto.post;

import java.util.UUID;

public record CreatePostResponseDto(UUID userId, String content) {
}
