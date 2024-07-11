package social.media.socialMedia.exception.user;

public class UserNotFoundException extends UserOperationException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}