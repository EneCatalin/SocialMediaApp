package com.socialmedia.posts.dto.post;

import com.socialmedia.posts.entity.Post;

import java.util.List;
import java.util.UUID;

//TODO: should I really use a List<Post>?
public record PostsByUserIdDto(UUID userId, List<Post> posts) {
}
