package com.socialmedia.posts.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "post_service_users")
public class PostServiceUser {

    @Id
    @Column(name = "id", unique = true,nullable = false, updatable = false)
    private UUID id;

    @Column(length = 255, unique = true, nullable = false)
    private String username;

    @Column(length = 255, unique = true, nullable = false)
    private String email;

//    @Column(length = 512)
//    private String profilePicture;
    @JsonManagedReference // This will manage the forward part of reference during serialization
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;  // One user can have many posts

    // Default constructor
    public PostServiceUser() {
    }

    // Constructor with parameters
    public PostServiceUser(UUID id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "PostServiceUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
