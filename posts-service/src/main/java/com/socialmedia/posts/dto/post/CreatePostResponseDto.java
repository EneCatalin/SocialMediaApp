package com.socialmedia.posts.dto.post;

import java.util.UUID;

public record CreatePostResponseDto(UUID userId, UUID postId, String content) {
}
