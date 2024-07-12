package com.socialmedia.posts.listener;

import com.socialmedia.posts.constants.MessagingConstants;
import com.socialmedia.posts.dto.UserEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventsListener {
    private static final Logger logger = LoggerFactory.getLogger(UserEventsListener.class);


    @RabbitListener(queues = "user-events-queue")
    public void receiveMessage(UserEvent event) {
        logger.info("EVENT RECEIVED: {}", event);

        switch (event.eventType()) {
            case MessagingConstants.USER_CREATED:
                logger.info("Handling created user event for user: {}", event.username());
                handleUserCreated(event);
                break;
            case MessagingConstants.USER_UPDATED:
                logger.info("Handling updated user event for user: {}", event.username());
                handleUserUpdated(event);
                break;
            case MessagingConstants.USER_DELETED:
                logger.info("Handling deleted user event for user: {}", event.username());
                handleUserDeleted(event);
                break;
            default:
                logger.error("Unknown event type: {}", event.eventType());
                break;
        }
    }

    private void handleUserCreated(UserEvent event) {
        // Logic for handling user creation
        logger.info("User created event handled for: {}", event.username());
    }

    private void handleUserUpdated(UserEvent event) {
        // Logic for handling user updates
        logger.info("User updated event handled for: {}", event.username());
    }

    private void handleUserDeleted(UserEvent event) {
        // Logic for handling user deletion
        logger.info("User deleted event handled for: {}", event.username());
    }
}
