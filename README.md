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

TODO: check if schema is good (written 06/06/2024)

(Local) Application Swagger:
http://localhost:8080/swagger-ui/index.html?continue#/User%20Management/checkServiceHealth


Thoughts on exception handling:

Since I've just started implementing them I am still unsure when to throw them and when not to. Ultimately it seems to be up for discussion/interpretation in certain situations. I will try to keep them for cases where it makes at least some sense but otherwise use Optional



So, I am working on a project and I think I am ready to start working on a new microservice before I can start the interviews process again. For my project I am building a social media application that will use microservices to allow users to make posts, make comments to posts and in the end have 1 on 1 chats that will be saved in a database. It will also have authentification and authorization (a very simple one at that). I also plan to use KeyCloack in the future but I hate this part of the application so I am saving it for last.

What I have implemented right now is a users service with a very simple OAuth implementation (hardly working, more of a POC) that doesn't even jwts yet. It also has a very basic authorization model for the ADMINS and regular users so they can access different routes/functionalities. The roles are saved in a separate DB table.

For a tech overview those are my gradle dependencies:

dependencies {
//OAuth
implementation 'org.springframework.boot:spring-boot-starter-security'
implementation 'org.springframework.boot:spring-boot-starter-oauth2-client'
implementation 'org.springframework.boot:spring-boot-starter-oauth2-resource-server'

	//JPA
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	runtimeOnly 'org.postgresql:postgresql:42.2.20'

	implementation 'org.springframework.boot:spring-boot-starter-web'
	developmentOnly 'org.springframework.boot:spring-boot-devtools'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testImplementation 'org.springframework.security:spring-security-test'

	// Swagger / OpenAPI 3 dependencies
	implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0'

	// mapStruct
	implementation 'org.mapstruct:mapstruct:1.5.3.Final'
	annotationProcessor 'org.mapstruct:mapstruct-processor:1.5.3.Final'


	// JWT to use later
	implementation 'io.jsonwebtoken:jjwt-api:0.11.2'
	implementation 'io.jsonwebtoken:jjwt-impl:0.11.2'
	implementation 'io.jsonwebtoken:jjwt-jackson:0.11.2'

	tasks.named('test') {
		useJUnitPlatform()
	}
}

[//]: # (GPT CHAT)
Certainly! Given the requirements of your social media application, here's a suggested design for splitting the microservices and their respective functionalities:

### Microservices Overview:

1. **User Service**
2. **Post Service**
3. **Comment Service**
4. **Chat Service**
5. **Notification Service**
6. **Authentication and Authorization Service**
7. **Gateway Service**
8. **Configuration Service**
9. **Discovery Service**

### Detailed Microservice Breakdown:

#### 1. User Service
**Responsibilities:**
- Manage user accounts and profiles.
- Handle user roles and permissions (USER, ADMIN).
- Provide endpoints for user registration, updates, and retrieval.

**Key Endpoints:**
- `POST /users` - Register a new user.
- `GET /users/{id}` - Retrieve user details.
- `PUT /users/{id}` - Update user information.
- `DELETE /users/{id}` - Delete a user account.

#### 2. Post Service
**Responsibilities:**
- Manage user posts.
- Handle creating, updating, retrieving, and deleting posts.
- Integrate with the Comment Service for post comments.

**Key Endpoints:**
- `POST /posts` - Create a new post.
- `GET /posts/{id}` - Retrieve a specific post.
- `GET /posts` - Retrieve all posts.
- `PUT /posts/{id}` - Update a post.
- `DELETE /posts/{id}` - Delete a post.

#### 3. Comment Service
**Responsibilities:**
- Manage comments on posts.
- Handle creating, updating, retrieving, and deleting comments.

**Key Endpoints:**
- `POST /comments` - Add a new comment to a post.
- `GET /comments/{id}` - Retrieve a specific comment.
- `GET /comments?postId={postId}` - Retrieve comments for a specific post.
- `PUT /comments/{id}` - Update a comment.
- `DELETE /comments/{id}` - Delete a comment.

#### 4. Chat Service
**Responsibilities:**
- Manage 1 on 1 chat sessions between users.
- Handle sending, receiving, and storing messages.

**Key Endpoints:**
- `POST /chats` - Initiate a new chat session.
- `GET /chats/{id}` - Retrieve a specific chat session.
- `POST /chats/{id}/messages` - Send a message in a chat session.
- `GET /chats/{id}/messages` - Retrieve messages in a chat session.

#### 5. Notification Service
**Responsibilities:**
- Manage user notifications.
- Handle sending notifications for new posts, comments, and messages.

**Key Endpoints:**
- `POST /notifications` - Create a new notification.
- `GET /notifications?userId={userId}` - Retrieve notifications for a specific user.

#### 6. Authentication and Authorization Service
**Responsibilities:**
- Manage user authentication and authorization.
- Handle OAuth2 and JWT-based authentication.
- Provide endpoints for login and token management.

**Key Endpoints:**
- `POST /auth/login` - Authenticate a user and provide a JWT.
- `POST /auth/refresh` - Refresh a JWT.
- `POST /auth/logout` - Logout a user and invalidate the JWT.

#### 7. Gateway Service
**Responsibilities:**
- Act as an API gateway to route requests to appropriate microservices.
- Provide a unified entry point for all client requests.
- Handle load balancing and security concerns.

**Technology:**
- Spring Cloud Gateway

#### 8. Configuration Service
**Responsibilities:**
- Manage and serve configuration properties for all microservices.
- Centralize configuration management to simplify updates and scaling.

**Technology:**
- Spring Cloud Config Server

#### 9. Discovery Service
**Responsibilities:**
- Handle service discovery to enable microservices to find each other.
- Manage dynamic scaling and availability of services.

**Technology:**
- Eureka

### Interaction Diagram:
```
Client
  |
  v
Gateway Service
  |
  +---------------------+-------------------+------------------+------------------+
  |                     |                   |                  |                  |
User Service       Post Service       Comment Service    Chat Service    Notification Service
  |                     |                   |                  |                  |
  +---------------------+                   |                  |                  |
        Authentication and Authorization Service             Configuration Service
                             |
                             v
                      Discovery Service
```

### Suggested Steps:

1. **Start with the Configuration Service**:
    - Set up Spring Cloud Config Server to manage configurations centrally.

2. **Set up the Discovery Service**:
    - Implement Eureka for service discovery to ensure all services can find and communicate with each other dynamically.

3. **Develop the Core Microservices**:
    - Begin with the User Service to manage user accounts and roles.
    - Follow with the Post Service and Comment Service to handle user posts and comments.
    - Implement the Chat Service for 1 on 1 chat functionality.

4. **Integrate Authentication and Authorization**:
    - Use OAuth2 and JWT in the Authentication and Authorization Service.
    - Ensure proper security and access controls across all services.

5. **Add the Notification Service**:
    - Implement notification logic and integrate it with other services for real-time updates.

6. **Implement the Gateway Service**:
    - Set up Spring Cloud Gateway to route and manage requests to the appropriate microservices.

7. **Containerize and Orchestrate**:
    - Use Docker to containerize each microservice.
    - Use Kubernetes (or another orchestration tool) to manage deployment, scaling, and operation of the containerized services.

This design ensures a modular, scalable, and maintainable architecture for your social media application, leveraging the power of microservices and Spring Cloud technologies.
