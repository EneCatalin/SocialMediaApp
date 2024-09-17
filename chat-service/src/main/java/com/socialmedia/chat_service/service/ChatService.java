package com.socialmedia.chat_service.service;

import com.socialmedia.chat_service.entity.Chat;
import com.socialmedia.chat_service.entity.Message;
import com.socialmedia.chat_service.entity.Participant;
import com.socialmedia.chat_service.entity.User;
import com.socialmedia.chat_service.repository.ChatRepository;
import com.socialmedia.chat_service.repository.MessageRepository;
import com.socialmedia.chat_service.repository.ParticipantRepository;
import com.socialmedia.chat_service.repository.UserRepository;
import dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChatService {
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final ParticipantRepository participantRepository;

    public ChatService(UserRepository userRepository, ChatRepository chatRepository,MessageRepository messageRepository, ParticipantRepository participantRepository) {
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.participantRepository = participantRepository;
    }


    // Method to get all chats a user is involved in
    public List<UUID> getUserChats(UUID userId) {
        return participantRepository.findByUserId(userId).stream()
                .map(participant -> participant.getChat().getId())
                .collect(Collectors.toList());
    }

    // Method to fetch messages for a chat
    public List<Message> getChatMessages(UUID chatId) {
        return messageRepository.findByChatIdOrderBySentAtAsc(chatId);
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

    public UserDto createUser(UserDto userDto) {
        logger.info("Creating user");

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        userRepository.save(user);

        return userRepository.getByUsername(userDto.getUsername());
    }

    public Chat startChat(UUID user1Id, UUID user2Id) {
        logger.info("Creating chat");
        Chat chat = new Chat();
        chat.setCreatedAt(LocalDateTime.now());
        chat.setUpdatedAt(LocalDateTime.now());
        chatRepository.save(chat);


        logger.info("Add user 1 " + user1Id);

        // Add user1 and user2 as participants
        Participant participant1 = new Participant();
        participant1.setChat(chat);
        participant1.setUser(userRepository.findById(user1Id).orElseThrow());
        participant1.setRole("participant");
        participantRepository.save(participant1);

        logger.info("add user 2" + user2Id);
        Participant participant2 = new Participant();
        participant2.setChat(chat);
        participant2.setUser(userRepository.findById(user2Id).orElseThrow());
        participant2.setRole("participant");
        participantRepository.save(participant2);

        return chat;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    private void deleteUserOrThrow(User user) {
        try {
            userRepository.delete(user);
            logger.info("User deleted successfully: {}", user);
        } catch (Exception e) {
            logger.error("Failed to delete user: {}", user, e);
            throw new RuntimeException("Failed to delete user: " + user, e);
        }
    }
    private Optional<User> findByUserId(UUID userId) {
        return userRepository.findById(userId);
    }

    public void deleteUserById(UUID userId) {

        User user = findByUserId(userId).orElseThrow(() -> new RuntimeException("User not found: " + userId));

        deleteUserOrThrow(user);
    }

}
