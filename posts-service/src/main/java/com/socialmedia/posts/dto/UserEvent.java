package com.socialmedia.posts.dto;

//TODO IMPLEMENT USER EVENT
//TOFIX implement user event

public record UserEvent(String eventType, Long userId, String username, String email) {

}

//public record UserEvent implements  UserEvent(String eventType, UUID userId, String username, String email) {
//}