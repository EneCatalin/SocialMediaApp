package com.socialmedia.posts.dto.post;

import java.util.UUID;

public record UpdatePostDtoResponse(UUID userId, UUID postId, String content) {
}
