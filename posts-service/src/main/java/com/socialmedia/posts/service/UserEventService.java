package com.socialmedia.posts.service;

import com.socialmedia.posts.dto.UserEvent;

public interface UserEventService {
    void handleUserCreated(UserEvent event);
    void handleUserUpdated(UserEvent event);
    void handleUserDeleted(UserEvent event);
}

