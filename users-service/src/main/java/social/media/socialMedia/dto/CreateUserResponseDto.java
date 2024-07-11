package social.media.socialMedia.dto;

import java.util.Set;

public record CreateUserResponseDto(String username, Set<String> roles, String bio, String email, String profilePicture) {}