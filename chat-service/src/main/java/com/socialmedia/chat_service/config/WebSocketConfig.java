package com.socialmedia.chat_service.config;

import com.socialmedia.chat_service.repository.ChatRepository;
import com.socialmedia.chat_service.repository.MessageRepository;
import com.socialmedia.chat_service.repository.UserRepository;
import com.socialmedia.chat_service.webSocket.WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {


    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    // Autowire the repositories into the config class
    @Autowired
    public WebSocketConfig(MessageRepository messageRepository, ChatRepository chatRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // Register WebSocketHandler with the required repositories
        registry.addHandler(new WebSocketHandler(messageRepository, chatRepository, userRepository), "/usersChat")
                .setAllowedOrigins("*");
    }
}