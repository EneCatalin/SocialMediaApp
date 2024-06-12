package social.media.socialMedia.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social.media.socialMedia.dto.CreateUserDto;
import social.media.socialMedia.dto.UserDto;
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

    @Operation(summary = "Check service health", description = "Returns the status of the user service")
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> checkServiceHealth() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/seed")
    public ResponseEntity<List<UserDto>> seedUsers() {
        List<UserDto> users = userService.seedUsers();

        return ResponseEntity.ok(users);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        UserDto user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @PostMapping()
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserDto user) {
        UserDto  createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }
}
