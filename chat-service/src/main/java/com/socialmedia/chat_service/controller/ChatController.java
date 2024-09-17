package com.socialmedia.chat_service.controller;

import com.socialmedia.chat_service.entity.Message;
import com.socialmedia.chat_service.entity.User;
import com.socialmedia.chat_service.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/seed")
    public ResponseEntity<List<User>> seedUsersAndChat() {
        List<User> seededUsers = chatService.seedChatService();
        return ResponseEntity.ok(seededUsers);
    }



    @GetMapping("/users/{userId}/chats")
    public ResponseEntity<List<UUID>> getUserChats(@PathVariable UUID userId) {
        List<UUID> chatIds = chatService.getUserChats(userId);
        return ResponseEntity.ok(chatIds);
    }

    @GetMapping("/chats/{chatId}/messages")
    public ResponseEntity<List<Message>> getChatHistory(@PathVariable UUID chatId) {
        List<Message> messages = chatService.getChatMessages(chatId);
        return ResponseEntity.ok(messages);
    }
}