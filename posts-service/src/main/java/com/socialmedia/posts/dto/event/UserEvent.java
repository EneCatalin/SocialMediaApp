package com.socialmedia.posts.dto.event;

import java.util.UUID;

public interface UserEvent {
    UUID getUserId();
    String getEventType();
}
