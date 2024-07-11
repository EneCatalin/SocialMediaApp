package social.media.socialMedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import social.media.socialMedia.entity.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String username);

    boolean existsByUsername(String username);
}