package social.media.socialMedia.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import social.media.socialMedia.dto.CreateUserDto;
import social.media.socialMedia.dto.UserDto;
import social.media.socialMedia.entity.User;
import social.media.socialMedia.exception.ResourceNotFoundException;
import social.media.socialMedia.repository.UserRepository;
import social.media.socialMedia.util.seed.Constants;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Convert User entity to UserDto
    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setBio(user.getBio());
        dto.setProfilePicture(user.getProfilePicture());
        return dto;
    }

    // Convert CreateUserDto to User entity
    private User convertToEntity(CreateUserDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setBio(dto.getBio());
        user.setProfilePicture(dto.getProfilePicture());
        return user;
    }


    public List<UserDto > getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public UserDto  getUserByUsername(String username) {
        logger.info("Fetching user with username: {}", username);
        User user = userRepository.findByUsername(username);

        //! Not sure this is exception worthy
        if (user == null) {
            throw new ResourceNotFoundException("User not found with username: " + username);
        }
        return convertToDto(user);
    }

    public List<UserDto> seedUsers() {
        Constants.seedUsersList.forEach(user -> user.setPassword(passwordEncoder.encode(user.getPassword())));
        return userRepository.saveAll(Constants.seedUsersList).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public UserDto  createUser(CreateUserDto  createUserDto) {
        User user = convertToEntity(createUserDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return convertToDto(userRepository.save(user));
    }

}
