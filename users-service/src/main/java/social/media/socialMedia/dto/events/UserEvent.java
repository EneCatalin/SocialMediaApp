package social.media.socialMedia.dto.events;

import java.util.UUID;

public interface UserEvent {
    UUID getUserId();
    String getEventType();
}
