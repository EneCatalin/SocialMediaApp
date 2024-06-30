package social.media.socialMedia.dto;

import java.util.Set;

//We do not want to return the password
public record FindByUsernameDto(String username, Set<String> roles, String bio, String email, String profilePicture) {}


