package com.socialmedia.chat_service.seed;

import com.socialmedia.chat_service.entity.Chat;
import com.socialmedia.chat_service.entity.Participant;
import com.socialmedia.chat_service.entity.User;
import com.socialmedia.chat_service.repository.ChatRepository;
import com.socialmedia.chat_service.repository.ParticipantRepository;
import com.socialmedia.chat_service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SeedChat {
    private static final Logger logger = LoggerFactory.getLogger(SeedChat.class);

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final ParticipantRepository participantRepository;

    public SeedChat(UserRepository userRepository, ChatRepository chatRepository, ParticipantRepository participantRepository) {
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.participantRepository = participantRepository;
    }

    public List<User> seedChatService() {
        logger.info("Seeding chat-service");

        // Create users
        User user1 = new User();
        user1.setUsername("JohnDoe");
        user1.setEmail("john@example.com");

        User user2 = new User();
        user2.setUsername("JaneDoe");
        user2.setEmail("jane@example.com");

        // Save users to the database
        userRepository.save(user1);
        userRepository.save(user2);

        // Create a common chat
        Chat chat = new Chat();
        chat.setCreatedAt(LocalDateTime.now());
        chat.setUpdatedAt(LocalDateTime.now());
        chatRepository.save(chat);

        // Add user1 and user2 to the chat (using Participants table)
        Participant participant1 = new Participant();
        participant1.setChat(chat);
        participant1.setUser(user1);
        participant1.setRole("participant");
        participantRepository.save(participant1);

        Participant participant2 = new Participant();
        participant2.setChat(chat);
        participant2.setUser(user2);
        participant2.setRole("participant");
        participantRepository.save(participant2);

        logger.info("Users added to the chat");

        // Return the seeded users
        List<User> seededUsers = new ArrayList<>();
        seededUsers.add(user1);
        seededUsers.add(user2);

        return seededUsers;
    }

}
