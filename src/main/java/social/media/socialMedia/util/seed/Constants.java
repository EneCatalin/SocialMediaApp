package social.media.socialMedia.util.seed;

import social.media.socialMedia.entity.User;

import java.util.List;

public class Constants {


    // Seed User
   public static final User user1 = new User("DemoUser","USER", "password123", "demo.user@example.com", "Demo User", "profilePicUrl");
   public static final User user2 = new User("TesterDoe", "USER","password123", "tester.doe@example.com", "Tester", "profilePicUrl");
   public static final User user3 = new User("DemoAdmin", "ADMIN","password123", "demo.admin@example.com", "Demo Admin", "profilePicUrl");
   public static final List<User> seedUsersList = List.of(user1, user2, user3);


}
