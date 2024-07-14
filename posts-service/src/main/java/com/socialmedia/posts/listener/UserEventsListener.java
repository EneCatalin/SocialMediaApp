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
                userEventService.handleUserCreated(event);
                break;
            case MessagingConstants.USER_UPDATED:
                userEventService.handleUserUpdated(event);
                break;
            case MessagingConstants.USER_DELETED:
                userEventService.handleUserDeleted(event);
                break;
            default:
                logger.error("Unknown event type: {}", event.eventType());
                break;
        }
    }
}
