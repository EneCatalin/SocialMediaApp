package social.media.socialMedia.exception.user;

public class UserUpdateException extends UserOperationException {
    public UserUpdateException(String message) {
        super(message);
    }

    public UserUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}