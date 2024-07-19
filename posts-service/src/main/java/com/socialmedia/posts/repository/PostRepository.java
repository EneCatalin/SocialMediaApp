package com.socialmedia.posts.repository;
import com.socialmedia.posts.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    //find posts by userId
    List<Post> findByUserId(UUID userId);
}

