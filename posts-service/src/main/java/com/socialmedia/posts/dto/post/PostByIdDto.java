package com.socialmedia.posts.dto.post;

import com.socialmedia.posts.entity.Post;

import java.util.UUID;

public record PostByIdDto(UUID userId, Post post) {
}
