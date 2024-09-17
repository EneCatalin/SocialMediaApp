package com.socialmedia.chat_service.exception.user;

public class UserAlreadyExists extends UserOperationException {
    public UserAlreadyExists(String message) {
        super(message);
    }
    public UserAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }

}
