package social.media.socialMedia.util.seed;

import social.media.socialMedia.entity.Role;
import social.media.socialMedia.entity.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Constants {

    // Seed Roles
    public static final Role userRole = new Role("ROLE_USER");
    public static final Role adminRole = new Role("ROLE_ADMIN");

    // Seed Users
    public static final User user1 = new User(
            "DemoUser",
            "password123",
            "demo.user@example.com",
            "Demo User",
            "profilePicUrl",
            new HashSet<>(Set.of(userRole))
    );

    public static final User user2 = new User(
            "TesterDoe",
            "password123",
            "tester.doe@example.com",
            "Tester",
            "profilePicUrl",
            new HashSet<>(Set.of(userRole))
    );

    public static final User user3 = new User(
            "DemoAdmin",
            "password123",
            "demo.admin@example.com",
            "Demo Admin",
            "profilePicUrl",
            new HashSet<>(Set.of(adminRole))
    );

    public static final List<User> seedUsersList = List.of(user1, user2, user3);

}
