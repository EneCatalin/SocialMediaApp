package social.media.socialMedia.service;

import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import social.media.socialMedia.dto.CreateUserDto;
import social.media.socialMedia.dto.CreateUserResponseDto;
import social.media.socialMedia.dto.FindByUsernameDto;
import social.media.socialMedia.dto.UpdateUserDto;
import social.media.socialMedia.entity.Role;
import social.media.socialMedia.entity.User;
import social.media.socialMedia.exception.ResourceNotFoundException;
import social.media.socialMedia.exception.event.UserEventPublisherException;
import social.media.socialMedia.exception.user.UserCreationException;
import social.media.socialMedia.exception.user.UserDeletionException;
import social.media.socialMedia.exception.user.UserNotFoundException;
import social.media.socialMedia.messaging.UserEventPublisher;
import social.media.socialMedia.repository.RoleRepository;
import social.media.socialMedia.repository.UserRepository;
import social.media.socialMedia.util.mapper.UserMapper;
import social.media.socialMedia.util.seed.Constants;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    UserEventPublisher userEventPublisher;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserEventPublisher userEventPublisher, UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserMapper userMapper) {
        this.userEventPublisher = userEventPublisher;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    private Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private Optional<User> findByUserId(UUID userId) {
        return userRepository.findById(userId);
    }

    private void saveUserOrThrow(User user) {
        try {
            userRepository.save(user);
            logger.info("User created successfully: {}", user);
        } catch (Exception e) {
            logger.error("Failed to save user: {}", user, e);
            throw new UserCreationException("Failed to save user: " + user, e);
        }
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

    private void publishUserCreatedEvent(User user) {
        try {
            userEventPublisher.publishUserCreated(user);
            logger.info("User CREATED event published: {}", user);
        } catch (Exception e) {
            logger.error("Failed to publish user CREATED event: {}", user, e);
            throw new UserEventPublisherException("Failed to publish user created event: " + user, e);
        }
    }

    private void publishUserUpdatedEvent(User user) {
        try {
            userEventPublisher.publishUserUpdated(user);
            logger.info("User UPDATED event published: {}", user);
        } catch (Exception e) {
            logger.error("Failed to publish user UPDATED event: {}", user, e);
            throw new UserEventPublisherException("Failed to publish user created event: " + user, e);
        }
    }

    private void publishDeletedUserEvent(User user) {
        try {
            userEventPublisher.publishUserDeleted(user);
            logger.info("User DELETED event published: {}", user);
        } catch (Exception e) {
            logger.error("Failed to publish user DELETED event: {}", user, e);
            throw new UserEventPublisherException("Failed to publish user created event: " + user, e);
        }
    }

    public List<FindByUsernameDto> getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll().stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    public FindByUsernameDto getUserByUsername(String username) {
        logger.info("Fetching user with username: {}", username);
        User user = findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found: " + username));

        return userMapper.userToUserDto(user);
    }

    public List<FindByUsernameDto> seedUsers() {
        // Save roles first
        roleRepository.saveAll(List.of(Constants.userRole, Constants.adminRole));

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("Role not found: ROLE_USER"));
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new ResourceNotFoundException("Role not found: ROLE_ADMIN"));

        List<User> preparedUsers = Constants.seedUsersList.stream().map(user -> {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Set<Role> roles = new HashSet<>();
            if (user.getUsername().equals("DemoAdmin")) {
                roles.add(adminRole);
            } else {
                roles.add(userRole);
            }
            user.setRoles(roles);
            return user;
        }).collect(Collectors.toList());

        List<User> savedUsers = userRepository.saveAll(preparedUsers);
        savedUsers.forEach(user -> {
            publishUserCreatedEvent(user); // Now the user has an ID
        });

        // Map users to DTOs
        return savedUsers.stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    private void checkUserExistsOrThrow(String username) throws EntityExistsException {
        if (userRepository.existsByUsername(username)) {
            logger.error("Attempted to create a user that already exists: {}", username);
            throw new EntityExistsException("User already exists with username: " + username);
        }
    }

    private User prepareUser(CreateUserDto createUserDto) {
        Role role = roleRepository.findByName(createUserDto.role())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + createUserDto.role()));

        User user = userMapper.createUserDtoToUser(createUserDto);
        user.setRoles(new HashSet<>(Set.of(role)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    @Transactional
    public CreateUserResponseDto createUser(CreateUserDto createUserDto) {
        checkUserExistsOrThrow(createUserDto.username());
        //prepare user for db
        User user = prepareUser(createUserDto);
        //save user
        saveUserOrThrow(user);

        // Publish event
        publishUserCreatedEvent(user);
        // Map to DTO and return
        return userMapper.createUserToCreateUserResponseDto(user);
    }


    @Transactional
    public void deleteUserById(UUID userId) {

        User user = findByUserId(userId).orElseThrow(() -> new UserNotFoundException("User not found: " + userId));

        deleteUserOrThrow(user);

        // Publish event
        publishDeletedUserEvent(user);
    }

    private void setUserRoles(User user, UpdateUserDto updateUserDto) {
        Set<Role> roles = updateUserDto.roles().stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + roleName)))
                .collect(Collectors.toSet());

        user.setRoles(roles);
    }

    @Transactional
    public FindByUsernameDto updateUser(UpdateUserDto updateUserDto) {
        User existingUser = findByUserId(updateUserDto.id())
                .orElseThrow(() -> new UserNotFoundException("User not found: " + updateUserDto.username()));

        logger.info("Updating user with username: {}", updateUserDto.username());
        setUserRoles(existingUser, updateUserDto);

        // Update other fields
        userMapper.updateUserDtoToUser(updateUserDto, existingUser);
        saveUserOrThrow(existingUser);

        publishUserUpdatedEvent(existingUser);

        return userMapper.userToUserDto(existingUser);
    }

}
