package com.socialmedia.chat_service.exception.chat;

public class MessagePersistenceException extends RuntimeException {
    public MessagePersistenceException(String message) {
        super(message);
    }
}