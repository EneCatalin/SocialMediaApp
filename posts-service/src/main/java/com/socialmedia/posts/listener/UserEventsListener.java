package com.socialmedia.posts.listener;

import com.socialmedia.posts.constants.MessagingConstants;
import com.socialmedia.posts.dto.UserEvent;
import com.socialmedia.posts.service.UserEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserEventsListener {
    private static final Logger logger = LoggerFactory.getLogger(UserEventsListener.class);

    @Autowired
    private UserEventService userEventService;

    @RabbitListener(queues = "user-events-queue")
    public void receiveMessage(UserEvent event) {
        logger.info("EVENT RECEIVED: {}", event);

        switch (event.eventType()) {
            case MessagingConstants.USER_CREATED:
                try{
                    userEventService.handleUserCreated(event);
                } catch (Exception e) {
                    logger.error("Failed to handle user created event: {}", event, e);
                }
                break;
            case MessagingConstants.USER_UPDATED:
                try {
                    userEventService.handleUserUpdated(event);
                } catch (Exception e) {
                    logger.error("Failed to handle user updated event: {}", event, e);
                }
                break;
            case MessagingConstants.USER_DELETED:
                try {
                    userEventService.handleUserDeleted(event);
                } catch (Exception e) {
                    logger.error("Failed to handle user deleted event: {}", event, e);
                }
                break;
            default:
                logger.error("Unknown event type: {}", event.eventType());
                break;
        }
    }
}
