package social.media.socialMedia.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social.media.socialMedia.dto.CreateUserDto;
import social.media.socialMedia.dto.CreateUserResponseDto;
import social.media.socialMedia.dto.FindByUsernameDto;
import social.media.socialMedia.dto.UpdateUserDto;
import social.media.socialMedia.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "APIs for managing users")
@ControllerAdvice
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Environment environment;

    @GetMapping("/login")
    public ResponseEntity<Void> redirectToGitHub() {
        String clientId = environment.getProperty("GITHUB_CLIENT_ID");
        String redirectUri = "http://localhost:8080/api/users/oauth/callback";

        String authorizationUrl = "https://github.com/login/oauth/authorize?" +
                "client_id=" + clientId +
                "&redirect_uri=" + redirectUri;

        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", authorizationUrl)
                .build();
    }

    @GetMapping("/oauth/callback")
    public ResponseEntity<Map<String, String>> handleOAuthCallback(@RequestParam("code") String authorizationCode) {
        Map<String, String> response = new HashMap<>();
        response.put("status", "OAuth callback received");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Check service health", description = "Returns the status of the user service")
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> checkServiceHealth() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/seed")
    public ResponseEntity<List<FindByUsernameDto>> seedUsers() {
        List<FindByUsernameDto> users = userService.seedUsers();

        return ResponseEntity.ok(users);
    }

    @GetMapping
    public ResponseEntity<List<FindByUsernameDto>> getAllUsers() {
        List<FindByUsernameDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{username}")
    public ResponseEntity<FindByUsernameDto> getUserByUsername(@PathVariable String username) {
        FindByUsernameDto user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @PostMapping()
    public ResponseEntity<CreateUserResponseDto> createUser(@RequestBody CreateUserDto user) {
        CreateUserResponseDto createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }


    @PutMapping
    public ResponseEntity<FindByUsernameDto> updateUser(@RequestBody UpdateUserDto user) {
        FindByUsernameDto updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping()
    public ResponseEntity<Map<String, String>> deleteByUsername() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");

        userService.deleteUserByUsername("admin");
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
