# SocialMediaApp

### Project Overview


Build a Social Media Application where users can create profiles, post updates, follow other users, and interact through comments and likes. This project will include multiple microservices, a REST API using Spring Boot, and an SQL database with multiple tables and complex queries.

### Components

#### User Service

* user registration
* authentication
* profiles
* following other users.

#### Post Service

* creating, updating, and deleting posts

#### Interaction Service

* Manages comments and likes on posts.

### Database Schema

#### Users

* id: PK
* username: Unique
* password: Encrypted
* email: Unique
* bio?: User bio
* profile_picture: URL or image?


#### Posts

* id: PK
* user_id: FK (Users)
* content: Text of comment
* created_at: Timestamp

#### Comments

* id: PK
* post_id: FK (Posts)
* user_id: FK (Users)
* content: Text of comment
* created_at: Timestamp

#### Likes

* id: PK
* post_id: FK (Posts)
* user_id: FK (Users)
* created_at: Timestamp

#### Followers: 

* id: PK
* user_id: FK (Posts)
* follower_id: FK (Users)
* created_at: Timestamp


(Local) Application Swagger:
http://localhost:8080/swagger-ui/index.html?continue#/User%20Management/checkServiceHealth
