package social.media.socialMedia.dto;

import java.util.Set;
import java.util.UUID;

//We do not want to return the password
public record FindByUsernameDto(UUID userId, String username, Set<String> roles, String bio, String email, String profilePicture) {}


