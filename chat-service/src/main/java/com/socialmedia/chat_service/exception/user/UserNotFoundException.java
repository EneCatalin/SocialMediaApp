package com.socialmedia.chat_service.exception.user;

public class UserNotFoundException extends UserOperationException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}