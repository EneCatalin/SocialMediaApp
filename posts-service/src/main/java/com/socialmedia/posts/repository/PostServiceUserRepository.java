package com.socialmedia.posts.repository;
import com.socialmedia.posts.entity.PostServiceUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostServiceUserRepository extends JpaRepository<PostServiceUser, UUID> {
}
