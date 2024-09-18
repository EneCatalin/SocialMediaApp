package com.socialmedia.chat_service.repository;

import com.socialmedia.chat_service.dto.UserDto;
import com.socialmedia.chat_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    UserDto getByUsername(String username);
}
