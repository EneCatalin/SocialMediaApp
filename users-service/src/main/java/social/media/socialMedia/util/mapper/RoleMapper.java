package social.media.socialMedia.util.mapper;

import org.mapstruct.Mapper;
import social.media.socialMedia.entity.Role;
import social.media.socialMedia.repository.RoleRepository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    default Set<String> mapRolesToRoleNames(Set<Role> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

    //TODO: TOFIX: this
    default Set<Optional<Role>> mapRoleNamesToRoles(Set<String> roleNames, RoleRepository roleRepository) {
        if (roleNames == null) {
            return null;
        }
        return roleNames.stream()
                .map(roleRepository::findByName)
                .collect(Collectors.toSet());
    }
}
