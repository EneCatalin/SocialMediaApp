package com.socialmedia.posts.controller;

import com.socialmedia.posts.dto.post.*;
import com.socialmedia.posts.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//TODO
//FIXME: add swagger

@RestController
@RequestMapping("/")
@ControllerAdvice
public class PostController {

    @Autowired
    private PostService posts;

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> checkServiceHealth() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<PostsByUserIdDto> getUserPosts(@PathVariable UUID userId) {
        PostsByUserIdDto responseDto = posts.getPostsByUserId(userId);
        return ResponseEntity.ok(responseDto);
    }


    @GetMapping("/")
    public ResponseEntity<PostByIdDto> getPostById(@RequestParam UUID userId, @RequestParam UUID postId) {
        PostByIdDto responseDto = posts.getPostById(userId, postId);
        return ResponseEntity.ok(responseDto);
    }


    @PostMapping("/{userId}")
    public ResponseEntity<CreatePostResponseDto> createPost(@PathVariable UUID userId, @RequestBody CreatePostDto createPostDto) {
        CreatePostResponseDto responseDto = posts.createPost(userId, createPostDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{userId}/{postId}")
    public ResponseEntity<Map<String, String>> deletePost(@PathVariable UUID userId, @PathVariable UUID postId) {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        posts.deletePost(userId, postId);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, String>> deleteAllPosts(@PathVariable UUID userId) {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        posts.deleteAllPosts(userId);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @PutMapping("/")
    public ResponseEntity<UpdatePostDtoResponse> updatePost(@RequestParam UUID userId, @RequestParam UUID postId, @RequestBody UpdatePostDto updatePostDto) {
        UpdatePostDtoResponse responseDto = posts.updatePost(userId, postId, updatePostDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
