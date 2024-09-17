package com.socialmedia.chat_service.exception.user;


public class UserDeletionException extends UserOperationException {

    public UserDeletionException(String message) {
        super(message);
    }

    public UserDeletionException(String message, Throwable cause) {
        super(message, cause);
    }

}
