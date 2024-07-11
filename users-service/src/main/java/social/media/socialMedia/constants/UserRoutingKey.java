package social.media.socialMedia.constants;

public enum UserRoutingKey {
    USER_CREATED("user.created"),
    USER_UPDATED("user.updated"),
    USER_DELETED("user.deleted");

    private final String key;

    UserRoutingKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
