package com.socialmedia.posts.service;

import com.socialmedia.posts.dto.UserEvent;
import com.socialmedia.posts.entity.PostServiceUser;
import com.socialmedia.posts.exception.UserCreationException;
import com.socialmedia.posts.repository.PostServiceUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserEventServiceImpl implements UserEventService {
    private static final Logger logger = LoggerFactory.getLogger(UserEventServiceImpl.class);

    @Autowired
    PostServiceUserRepository postServiceUserRepo;

    private void validateUserEvent(UserEvent event) {
        if (event.username() == null || event.email() == null || event.userId() == null) {
            logger.error("Invalid user event: {}", event);
            throw new IllegalArgumentException("DB POSSIBLY OUT OF SYNC. Invalid user event: " + event);
        }
        logger.info("Handling user event: {}", event.eventType());
    }

    private void checkIfUserExistsOrThrow(UUID userId) {
        if (!postServiceUserRepo.existsById(userId)) {
            logger.error("User not found: {}", userId);
            throw new IllegalArgumentException("User not found: " + userId);
        }
    }

    private PostServiceUser eventToPostServiceUser(UserEvent event) {
        return new PostServiceUser(event.userId(), event.username(), event.email());
    }

    private void saveUserOrThrow(PostServiceUser user) {
        try {
            postServiceUserRepo.save(user);
        } catch (Exception e) {
            logger.error("Failed to save user: {}", user, e);
            throw new UserCreationException("Failed to save user: " + user);
        }
    }

    private void deleteUserOrThrow(UUID userId) {
        try {
            postServiceUserRepo.deleteById(userId);
            logger.info("User deleted successfully: {}", userId);
        } catch (Exception e) {
            logger.error("Failed to handle user deletion event: {}{}", userId, e.getMessage(), e);
            // Handle retry or log to a database for future handling
        }
    }

    @Override
    public void handleUserCreated(UserEvent event) {
        validateUserEvent(event);
        saveUserOrThrow(eventToPostServiceUser(event));

        logger.info("User created successfully: {}", event);
    }

    @Override
    public void handleUserUpdated(UserEvent event) {
        validateUserEvent(event);
        checkIfUserExistsOrThrow(event.userId());
        saveUserOrThrow(eventToPostServiceUser(event));

        logger.info("User updated successfully: {}", event);
    }

    @Override
    public void handleUserDeleted(UserEvent event) {
        checkIfUserExistsOrThrow(event.userId());
        deleteUserOrThrow(event.userId());
    }
}
