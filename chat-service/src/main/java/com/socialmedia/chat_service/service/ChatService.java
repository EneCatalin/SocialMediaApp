package com.socialmedia.chat_service.service;

import com.socialmedia.chat_service.dto.MessageDto;
import com.socialmedia.chat_service.dto.UserDto;
import com.socialmedia.chat_service.entity.Chat;
import com.socialmedia.chat_service.entity.Message;
import com.socialmedia.chat_service.entity.Participant;
import com.socialmedia.chat_service.entity.User;
import com.socialmedia.chat_service.exception.user.UserAlreadyExists;
import com.socialmedia.chat_service.exception.user.UserDeletionException;
import com.socialmedia.chat_service.exception.user.UserNotFoundException;
import com.socialmedia.chat_service.repository.ChatRepository;
import com.socialmedia.chat_service.repository.MessageRepository;
import com.socialmedia.chat_service.repository.ParticipantRepository;
import com.socialmedia.chat_service.repository.UserRepository;
import com.socialmedia.chat_service.seed.SeedChat;
import com.socialmedia.chat_service.util.mapper.MessageMapper;
import com.socialmedia.chat_service.util.mapper.UserMapper;
import jakarta.persistence.EntityExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private final UserMapper userMapper;
    private final MessageMapper messageMapper;


    public ChatService(UserRepository userRepository, ChatRepository chatRepository, MessageRepository messageRepository, ParticipantRepository participantRepository, UserMapper userMapper, MessageMapper messageMapper) {
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.participantRepository = participantRepository;
        this.userMapper = userMapper;
        this.messageMapper = messageMapper;
    }

    private void checkUserExistsByIdOrThrow(UUID userId) throws EntityExistsException {
        if (userRepository.existsById(userId)) {
            logger.error("Attempted to create a user that already exists: {}", userId);
            throw new UserAlreadyExists("User already exists with username: " + userId);
        }
    }


    // Method to get all chats a user is involved in
    public List<UUID> getUserChats(UUID userId) {
        // Check if user exists
        checkUserExistsByIdOrThrow(userId);
        //should I check if user is a participant in any chat first?
        return participantRepository.findByUserId(userId).stream()
                .map(participant -> participant.getChat().getId())
                .collect(Collectors.toList());
    }

    // Method to fetch messages for a chat
    public List<MessageDto> getChatMessages(UUID chatId) {
        List<Message> messages = messageRepository.findByChatIdOrderBySentAtAsc(chatId);

        // Convert each Message entity to MessageDto using the mapper
        return messages.stream()
                .map(messageMapper::messageToMessageDto)
                .collect(Collectors.toList());
    }

    //not sure if this is the way to refactor it, but it should keep it separate
    public List<User> seedChatService() {
        return new SeedChat(userRepository,chatRepository,participantRepository).seedChatService();
    }

    public UserDto createUser(UserDto userDto) {
        logger.info("Creating user");

        // Use the mapper to convert the DTO to an entity
        User user = userMapper.userDtoToUser(userDto);

        // Save the user entity
        User savedUser = userRepository.save(user);

        // Map the saved entity back to UserDto (now a record)
        return userMapper.userToUserDto(savedUser);
    }


    //don't think this code really repeats in other places, safe to ignore for now
    //the participant role is an interesting one, need to think about roles later
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
        participant1.setUser(userRepository.findById(user1Id).orElseThrow(() -> new UserNotFoundException("User not found: " + user1Id)));
        participant1.setRole("participant");
        participantRepository.save(participant1);

        logger.info("add user 2" + user2Id);
        Participant participant2 = new Participant();
        participant2.setChat(chat);
        participant2.setUser(userRepository.findById(user2Id).orElseThrow(() -> new UserNotFoundException("User not found: " + user2Id)));
        participant2.setRole("participant");
        participantRepository.save(participant2);

        return chat;
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        // Use the mapper to convert the list of users to DTOs
        return users.stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    private void deleteUserOrThrow(User user) {
        try {
            userRepository.delete(user);
            logger.info("User deleted successfully: {}", user);
        } catch (Exception e) {
            logger.error("Failed to delete user: {}", user, e);
            throw new UserDeletionException("Failed to delete user: " + user, e);
        }
    }

    private Optional<User> findByUserId(UUID userId) {
        return userRepository.findById(userId);
    }

    public void deleteUserById(UUID userId) {

        User user = findByUserId(userId).orElseThrow(() -> new UserNotFoundException("User not found: " + userId));

        deleteUserOrThrow(user);
    }

    public Chat startGroupChat(List<UUID> userIds) {
        logger.info("Creating group chat");
        Chat chat = new Chat();
        chat.setCreatedAt(LocalDateTime.now());
        chat.setUpdatedAt(LocalDateTime.now());
        chatRepository.save(chat);

        // Add all users as participants in the chat
        for (UUID userId : userIds) {
            Participant participant = new Participant();
            participant.setChat(chat);
            participant.setUser(userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found: " + userId)));
            participant.setRole("participant"); // You can update roles later for specific use cases
            participantRepository.save(participant);
        }

        return chat;
    }
}
