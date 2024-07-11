package social.media.socialMedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import social.media.socialMedia.entity.Role;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String name);

    //TODO: pointless method, should be removed?
    boolean existsByName(String role);
}
