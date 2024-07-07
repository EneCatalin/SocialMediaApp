package social.media.socialMedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import social.media.socialMedia.entity.Role;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByName(String name);
}
