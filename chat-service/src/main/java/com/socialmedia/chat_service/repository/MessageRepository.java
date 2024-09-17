package com.socialmedia.chat_service.repository;

import com.socialmedia.chat_service.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    List<Message> findByChatIdOrderBySentAtAsc(UUID chatId);
}