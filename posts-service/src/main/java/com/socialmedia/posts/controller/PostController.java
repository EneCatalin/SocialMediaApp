package com.socialmedia.posts.controller;

import com.socialmedia.posts.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//TODO
//FIXME: add swagger

@RestController
@RequestMapping("/api/posts")
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
//
//    @GetMapping("/${userId}")
//    public ResponseEntity<PostsByUserIdDto> getUserPosts(@PathVariable UUID userId) {
//        PostsByUserIdDto responseDto = posts.getPostsByUserId(userId);
//        return ResponseEntity.ok(responseDto);
//    }

//
//    @GetMapping("/${userId}/${postId}")
//    public ResponseEntity<PostByIdDto> getPostById(@PathVariable UUID userId, @PathVariable UUID postId) {
//        PostByIdDto responseDto = posts.getPostById(userId, postId);
//        return ResponseEntity.ok(responseDto);
//    }
//
//
//    @PostMapping("/${userId}")
//    public ResponseEntity<CreatePostResponseDto> createPost(@PathVariable UUID userId, @RequestBody CreatePostDto createPostDto) {
//        CreatePostResponseDto responseDto = posts.createPost(userId, createPostDto);
//        return ResponseEntity.ok(responseDto);
//    }
//
//    @DeleteMapping("/${userId}/${postId}")
//    public ResponseEntity<Map<String, String>> deletePost(@PathVariable UUID userId, @PathVariable UUID postId) {
//        Map<String, String> response = new HashMap<>();
//        response.put("status", "UP");
//        posts.deletePost(userId, postId);
//        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
//    }
//
//    @DeleteMapping("/${userId}")
//    public ResponseEntity<Map<String, String>> deleteAllPosts(@PathVariable UUID userId) {
//        Map<String, String> response = new HashMap<>();
//        response.put("status", "UP");
//        posts.deleteAllPosts(userId);
//        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
//    }
//
//    @PutMapping("/${userId}/${postId}")
//    public ResponseEntity<Map<String, String>> updatePost(@PathVariable UUID userId, @PathVariable UUID postId, @RequestBody UpdatePostDto updatePostDto) {
//        Map<String, String> response = new HashMap<>();
//        response.put("status", "UP");
//        posts.updatePost(userId, postId, updatePostDto);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

}
