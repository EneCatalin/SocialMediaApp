package com.socialmedia.posts.service;

import com.socialmedia.posts.dto.post.PostsByUserIdDto;
import com.socialmedia.posts.dto.post.CreatePostDto;
import com.socialmedia.posts.dto.post.CreatePostResponseDto;
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





    //TODO: TOFIX: this could be wrong, check the response and the dto
    public PostsByUserIdDto getPostsByUserId(UUID userId) {
        // Fetch the user from your user repository
        PostServiceUser user = postServiceUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("No user found with ID: " + userId));

        // Fetch the posts for the user from your repository
        List<Post> posts = postRepository.findByUserId(userId);

        // Create a new PostsByUserIdDto with the user and posts
        return new PostsByUserIdDto(userId, posts);
    }

    public CreatePostResponseDto createPost(UUID userId, CreatePostDto createPostDto) {
        // Fetch the user from your user repository
        PostServiceUser user = postServiceUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("No user found with ID: " + userId));

        // Create a new Post entity
        Post newPost = new Post(createPostDto.content(), user);

        // Save the new post to the repository
        newPost = postRepository.save(newPost);

        logger.info("Created post {}", newPost);

        // Assuming you have a method to convert Post to CreatePostResponseDto
        return new CreatePostResponseDto(newPost.getId(), newPost.getContent());
    }



}
