package com.example.task_manager_backend.controller;

import com.example.task_manager_backend.dto.LoginRequestDTO;
import com.example.task_manager_backend.dto.SignupRequestDTO;
import com.example.task_manager_backend.model.User;
import com.example.task_manager_backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Sign up a new user")
    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignupRequestDTO signupRequest) {
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(signupRequest.getPassword());
        return ResponseEntity.ok(userService.signup(user));
    }

    @Operation(summary = "Log in a user")
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequestDTO loginRequest) {
        User user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(401).build();
    }

    @Operation(summary = "Update user profile with optional photo")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateProfile(
            @PathVariable Long id,
            @RequestPart("user") User user,
            @RequestPart(value = "profilePhoto", required = false) MultipartFile profilePhoto) throws IOException {
        return ResponseEntity.ok(userService.updateProfile(id, user, profilePhoto));
    }

    @Operation(summary = "Get user by ID")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }
}