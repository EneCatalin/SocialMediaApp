package com.socialmedia.chat_service.exception.user;

public class UserOperationException extends RuntimeException {
    public UserOperationException(String message) {
        super(message);
    }

    public UserOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
