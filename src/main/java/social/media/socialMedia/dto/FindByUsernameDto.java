package social.media.socialMedia.dto;

//We do not want to return the password
public record FindByUsernameDto(String username, String email, String profilePicture) {}


