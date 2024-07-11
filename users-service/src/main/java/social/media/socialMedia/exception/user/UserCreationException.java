package social.media.socialMedia.exception.user;

public class UserCreationException extends UserOperationException {
    public UserCreationException(String message) {
        super(message);
    }

    public UserCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}