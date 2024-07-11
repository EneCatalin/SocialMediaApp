package social.media.socialMedia.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import social.media.socialMedia.constants.MessagingConstants;
import social.media.socialMedia.constants.UserRoutingKey;
import social.media.socialMedia.dto.events.UserCreatedEvent;
import social.media.socialMedia.dto.events.UserDeletedEvent;
import social.media.socialMedia.dto.events.UserEvent;
import social.media.socialMedia.dto.events.UserUpdatedEvent;
import social.media.socialMedia.entity.User;

@Service
public class UserEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(UserEventPublisher.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    private final String exchange = MessagingConstants.USER_EVENTS_EXCHANGE;

    public void publishUserCreated(User user) {
        UserEvent userCreatedEvent = new UserCreatedEvent(user.getId(), user.getUsername(), user.getEmail());
        logger.info("Publishing User Created Event: {}", userCreatedEvent);
        amqpTemplate.convertAndSend(exchange, UserRoutingKey.USER_CREATED.getKey(), userCreatedEvent);
    }

    public void publishUserUpdated(User user) {
        UserEvent userUpdatedEvent = new UserUpdatedEvent(user.getId(), user.getUsername(), user.getEmail());
        logger.info("Publishing User Updated Event: {}", userUpdatedEvent);
        amqpTemplate.convertAndSend(exchange, UserRoutingKey.USER_UPDATED.getKey(), userUpdatedEvent);
    }

    public void publishUserDeleted(User user) {
        UserEvent userDeletedEvent = new UserDeletedEvent(user.getId(), user.getEmail());
        logger.info("Publishing User Deleted Event: {}", userDeletedEvent);
        amqpTemplate.convertAndSend(exchange, UserRoutingKey.USER_DELETED.getKey(), userDeletedEvent);
    }
}