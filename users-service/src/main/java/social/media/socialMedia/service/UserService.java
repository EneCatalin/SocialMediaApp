package social.media.socialMedia.service;

import jakarta.persistence.EntityExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import social.media.socialMedia.dto.CreateUserDto;
import social.media.socialMedia.dto.FindByUsernameDto;
import social.media.socialMedia.entity.Role;
import social.media.socialMedia.entity.User;
import social.media.socialMedia.exception.ResourceNotFoundException;
import social.media.socialMedia.exception.user.UserCreationException;
import social.media.socialMedia.messaging.UserEventPublisher;
import social.media.socialMedia.repository.RoleRepository;
import social.media.socialMedia.repository.UserRepository;
import social.media.socialMedia.util.mapper.UserMapper;
import social.media.socialMedia.util.seed.Constants;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserEventPublisher userEventPublisher;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserMapper userMapper;

    private User findByUsername(String username) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(username));
        return userOptional.orElse(null);
    }

    public List<FindByUsernameDto> getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll().stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    public FindByUsernameDto getUserByUsername(String username) {
        logger.info("Fetching user with username: {}", username);
        User user = findByUsername(username);

        //! Not sure if this is exception worthy
        if (user == null) {
            logger.error("User not found with username: {}", username);
            throw new ResourceNotFoundException("User not found with username: " + username);
        }

        return userMapper.userToUserDto(user);
    }

    public List<FindByUsernameDto> seedUsers() {
        // Save roles first
        roleRepository.saveAll(List.of(Constants.userRole, Constants.adminRole));

        // Encode passwords and set roles
        Constants.seedUsersList.forEach(user -> {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            // Set roles based on predefined roles
            //TODO: TOFIX:
            Optional<Role> role = roleRepository.findByName(user.getUsername().equals("DemoAdmin") ? "ROLE_ADMIN" : "ROLE_USER");

            assert role.orElse(null) != null;
            user.setRoles(new HashSet<>(Set.of(role.orElse(null))));
        });

        // Save users
        List<User> users = userRepository.saveAll(Constants.seedUsersList);

        // Map users to DTOs
        return users.stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    public FindByUsernameDto createUser(CreateUserDto createUserDto) {
        // Check if user already exists
        if (userRepository.existsByUsername(createUserDto.username())) {
            logger.error("Attempted to create a user that already exists: {}", createUserDto.username());
            throw new EntityExistsException("User already exists with username: " + createUserDto.username());
        }

        // Get role or throw if not found
        Role role = roleRepository.findByName(createUserDto.role())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + createUserDto.role()));

        // Map DTO to entity and set properties
        User user = userMapper.createUserDtoToUser(createUserDto);
        user.setRoles(new HashSet<>(Set.of(role)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save user

        User savedUser;
        try {
            savedUser= userRepository.save(user);
            logger.info("User created successfully: {}", savedUser);
        } catch (Exception e) {
            logger.error("Failed to save user: {}", user, e);
            throw new UserCreationException("Failed to save user: " + user, e);
        }
        logger.info("User created successfully: {}", savedUser);

        // Publish event
        try {
            userEventPublisher.publishUserCreated(savedUser);
        } catch (Exception e) {
            logger.error("Failed to publish user created event for user: {}", savedUser, e);
            // Consider how critical it is to handle event publishing failure
        }

        // Map to DTO and return
        return userMapper.userToUserDto(savedUser);
    }


    public void deleteUserByUsername(String username) {
        logger.info("Deleting user with username: {}", username);
        User user = findByUsername(username);
        if (user != null) {
            userRepository.delete(user);
        } else {
            logger.error("User not found with username: {}", username);
            throw new ResourceNotFoundException("User not found with username: " + username);
        }
    }

}
