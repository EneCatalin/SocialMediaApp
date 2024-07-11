package social.media.socialMedia.dto;

import java.util.Set;
import java.util.UUID;

public record UpdateUserDto(UUID id, String username, Set<String> roles, String bio, String email, String profilePicture) {
}
