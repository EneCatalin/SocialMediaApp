package com.socialmedia.chat_service.webSocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialmedia.chat_service.dto.IncomingMessage;
import com.socialmedia.chat_service.entity.Chat;
import com.socialmedia.chat_service.entity.Message;
import com.socialmedia.chat_service.entity.User;
import com.socialmedia.chat_service.repository.ChatRepository;
import com.socialmedia.chat_service.repository.MessageRepository;
import com.socialmedia.chat_service.repository.UserRepository;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;
import java.util.Optional;

public class WebSocketHandler extends TextWebSocketHandler {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;  // Corrected
    private final UserRepository userRepository;  // Corrected

    public WebSocketHandler(MessageRepository messageRepository, ChatRepository chatRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        IncomingMessage incomingMessage = objectMapper.readValue(textMessage.getPayload(), IncomingMessage.class);

        Optional<Chat> chat = chatRepository.findById(incomingMessage.chatId);
        Optional<User> sender = userRepository.findById(incomingMessage.senderId);

        if (chat.isPresent() && sender.isPresent()) {
            // Create and save message as usual
            Message message = new Message();
            message.setChat(chat.get());
            message.setSender(sender.get());
            message.setContent(incomingMessage.content);
            message.setSentAt(LocalDateTime.now());

            messageRepository.save(message);
        } else {
            System.out.println("Invalid chat or user ID!");
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Logic to handle when a new connection is established
        System.out.println("Connection Established: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Logic to handle when a connection is closed
        System.out.println("Connection Closed: " + session.getId());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        // Custom logic to handle transport errors (logging, cleaning up, etc.)
        System.out.println("Transport Error: " + exception.getMessage());
    }
}