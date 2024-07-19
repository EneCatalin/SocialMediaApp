package com.socialmedia.posts.dto.post;

import java.util.UUID;

public record CreatePostDto(UUID userId, String content) {
}
