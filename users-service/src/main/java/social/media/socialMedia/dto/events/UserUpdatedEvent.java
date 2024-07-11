package social.media.socialMedia.dto.events;

import java.util.UUID;

public record UserUpdatedEvent(UUID userId, String username, String email) implements UserEvent {

    @Override
    public UUID getUserId() {
        return userId;
    }

    @Override
    public String getEventType() {
        return "USER_UPDATED";
    }
}
