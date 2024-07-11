package com.socialmedia.posts.listener;

import com.socialmedia.posts.dto.UserEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class UserEventsListener {
    private static final Logger logger = LoggerFactory.getLogger(UserEventsListener.class);

    @RabbitListener(queues = "user-events-queue")
    @Payload(required = false)
    public void receiveMessage(UserEvent event) {
        logger.error("THE EVENT IS : {}", event);

        switch (event.eventType()) {
            case "CREATED":
                logger.info("CREATED EVENT RECEIVED: {}", event);
                handleUserCreated(event);
                break;
            case "UPDATED":
                logger.info("UPDATED EVENT RECEIVED: {}", event);
                handleUserUpdated(event);
                break;
            case "DELETED":
                logger.info("DELETED EVENT RECEIVED: {}", event);
                handleUserDeleted(event);
                break;
            default:
                //TOFIX - Handle unknown event type
                logger.error("Unknown event type: {}", event.eventType());
                break;
        }
    }

    private void handleUserCreated(UserEvent event) {
        logger.info("CREATED EVENT RECEIVED HANDLED: {}", event);

        // Logic for handling user creation
    }

    private void handleUserUpdated(UserEvent event) {
        logger.info("UPDATED EVENT RECEIVED HANDLED: {}", event);

        // Logic for handling user updates
    }

    private void handleUserDeleted(UserEvent event) {
        logger.info("DELETED EVENT RECEIVED HANDLED: {}", event);

        // Logic for handling user deletion
    }
}
