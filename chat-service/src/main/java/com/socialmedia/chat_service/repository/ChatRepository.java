package com.socialmedia.chat_service.repository;

import com.socialmedia.chat_service.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID> {
    // Custom query methods if needed
}