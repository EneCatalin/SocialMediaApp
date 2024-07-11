package social.media.socialMedia.exception.event;

public class UserEventPublisherException extends RuntimeException {
    public UserEventPublisherException(String message) {
        super(message);
    }

    public UserEventPublisherException(String message, Throwable cause) {
        super(message, cause);
    }
}
