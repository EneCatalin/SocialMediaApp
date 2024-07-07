package social.media.socialMedia.service;

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
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserMapper userMapper;

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
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

        //! Not sure this is exception worthy
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
            Role role = roleRepository.findByName(user.getUsername().equals("DemoAdmin") ? "ROLE_ADMIN" : "ROLE_USER");
            user.setRoles(new HashSet<>(Set.of(role)));
        });

        // Save users
        List<User> users = userRepository.saveAll(Constants.seedUsersList);

        // Map users to DTOs
        return users.stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }
//    public List<FindByUsernameDto> seedUsers() {
//        // Save roles first
//        roleRepository.saveAll(List.of(Constants.userRole, Constants.adminRole));
//
//        // Encode passwords
//        Constants.seedUsersList.forEach(user -> user.setPassword(passwordEncoder.encode(user.getPassword())));
//
//        // Save users
//        List<User> users = userRepository.saveAll(Constants.seedUsersList);
//
//        // Map users to DTOs
//        return users.stream()
//                .map(userMapper::userToUserDto)
//                .collect(Collectors.toList());
//    }

    public FindByUsernameDto createUser(CreateUserDto createUserDto) {
        User user = userMapper.createUserDtoToUser(createUserDto);

        // Find role from the database
        Role role = roleRepository.findByName("ROLE_USER"); // Assuming all new users get "ROLE_USER" role
        user.setRoles(new HashSet<>(Set.of(role)));

        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save user
        User savedUser = userRepository.save(user);

        // Map to DTO
        return userMapper.userToUserDto(savedUser);
    }




//    public List<FindByUsernameDto> seedUsers() {
//        Constants.seedUsersList.forEach(user -> user.setPassword(encodePassword(user.getPassword())));
//        return userRepository.saveAll(Constants.seedUsersList).stream()
//                .map(userMapper::userToUserDto)
//                .collect(Collectors.toList());
//    }

//    public FindByUsernameDto createUser(CreateUserDto  createUserDto) {
//        User user = userMapper.createUserDtoToUser(createUserDto);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//        return userMapper.userToUserDto(userRepository.save(user));
//    }


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
