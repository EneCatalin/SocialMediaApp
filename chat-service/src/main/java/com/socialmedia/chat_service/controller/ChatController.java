package com.socialmedia.chat_service.controller;

import com.socialmedia.chat_service.dto.ChatRequest;
import com.socialmedia.chat_service.dto.MessageDto;
import com.socialmedia.chat_service.dto.UserDto;
import com.socialmedia.chat_service.entity.Chat;
import com.socialmedia.chat_service.entity.User;
import com.socialmedia.chat_service.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/")
@ControllerAdvice
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    //TODO: return DTO -> maybe ignore for seed, this should be a dev route anyway
    @PostMapping("/seed")
    public ResponseEntity<List<User>> seedUsersAndChat() {
        List<User> seededUsers = chatService.seedChatService();
        return ResponseEntity.ok(seededUsers);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        logger.info("Creating user");
        UserDto responseDto = chatService.createUser(userDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/chats")
    public ResponseEntity<Chat> startChat(@RequestBody ChatRequest chatRequest) {
        Chat chat = chatService.startChat(chatRequest.getUser1Id(), chatRequest.getUser2Id());
        return ResponseEntity.ok(chat);
    }

    //TODO return DTO
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = chatService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    //TODO: delete once you connect with the other services?
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable UUID userId) {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");

        chatService.deleteUserById(userId);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/users/{userId}/chats")
    public ResponseEntity<List<UUID>> getUserChats(@PathVariable UUID userId) {
        List<UUID> chatIds = chatService.getUserChats(userId);
        return ResponseEntity.ok(chatIds);
    }

    //TODO return DTO
    @GetMapping("/chats/{chatId}/messages")
    public ResponseEntity<List<MessageDto>> getChatHistory(@PathVariable UUID chatId) {
        List<MessageDto> messages = chatService.getChatMessages(chatId);
        return ResponseEntity.ok(messages);
    }
}