package com.socialmedia.posts.service;

import com.socialmedia.posts.dto.PostsByUserIdDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    public PostsByUserIdDto getPostsByUserId(UUID userId) {
        return null;
    }
}
