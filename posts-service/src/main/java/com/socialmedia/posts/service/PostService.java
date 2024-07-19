package com.socialmedia.posts.service;

import com.socialmedia.posts.dto.post.*;
import com.socialmedia.posts.entity.Post;
import com.socialmedia.posts.entity.PostServiceUser;
import com.socialmedia.posts.repository.PostRepository;
import com.socialmedia.posts.repository.PostServiceUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    PostRepository postRepository;

    @Autowired
    private PostServiceUserRepository postServiceUserRepository;

    void checkIfUserExistsOrThrow(UUID userId) {
        logger.info("Checking if user exists: {}", userId);
        postServiceUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("No user found with ID: " + userId));
    }

    PostServiceUser getPostServiceUserById(UUID userId) {
        logger.info("getPostServiceUserById for user: {}", userId);
        return postServiceUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("No user found with ID: " + userId));
    }

    //TODO: TOFIX: this could be wrong, check the response and the dto
    public PostsByUserIdDto getPostsByUserId(UUID userId) {
        checkIfUserExistsOrThrow(userId);
        logger.info("Retrieving posts for user {}", userId);
        List<Post> posts = postRepository.findByUserId(userId);
        return new PostsByUserIdDto(userId, posts);
    }

    public CreatePostResponseDto createPost(UUID userId, CreatePostDto createPostDto) {
        PostServiceUser user = getPostServiceUserById(userId);
        Post newPost = new Post(createPostDto.content(), user);
        newPost = postRepository.save(newPost);

        logger.info("Created post {}", newPost);

        return new CreatePostResponseDto(newPost.getUser().getId(),newPost.getId(), newPost.getContent());
    }


    public PostByIdDto getPostById(UUID userId, UUID postId) {
        checkIfUserExistsOrThrow(userId);
        Post post = (Post) postRepository.findByIdAndUserId(postId, userId).orElseThrow();

        logger.info("Retrieved post {}", post);
        return new PostByIdDto(userId, post);
    }

    public void deleteAllPosts(UUID userId) {
        checkIfUserExistsOrThrow(userId);

        logger.info("Deleting all posts for user {}", userId);
        postRepository.deleteAllByUserId(userId);
    }


    public void deletePost(UUID userId, UUID postId) {
        checkIfUserExistsOrThrow(userId);
        logger.info("Deleting post {}", postId);
        postRepository.deleteByIdAndUserId(postId, userId);
    }

    public UpdatePostDtoResponse updatePost(UUID userId, UUID postId, UpdatePostDto updatePostDto) {
        checkIfUserExistsOrThrow(userId);
        Post post = (Post) postRepository.findByIdAndUserId(postId, userId).orElseThrow();
        post.setContent(updatePostDto.content());

        logger.info("Updated post {}", post);
        postRepository.save(post);
        return new UpdatePostDtoResponse(post.getUser().getId(), post.getId(),updatePostDto.content());
    }
}
