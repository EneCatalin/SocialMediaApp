package social.media.socialMedia.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import social.media.socialMedia.entity.User;
import social.media.socialMedia.exception.ResourceNotFoundException;
import social.media.socialMedia.repository.UserRepository;
import social.media.socialMedia.util.seed.Constants;

import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        logger.info("Fetching user with username: {}", username);
        User user = userRepository.findByUsername(username);

        //! Not sure this is exception worthy
        if (user == null) {
            throw new ResourceNotFoundException("User not found with username: " + username);
        }
        return user;
    }

    public List<User> seedUsers() {
        Constants.seedUsersList.forEach(user -> user.setPassword(passwordEncoder.encode(user.getPassword())));
        return userRepository.saveAll(Constants.seedUsersList);
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

}
