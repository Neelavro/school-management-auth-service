package com.example.auth_service.controller;

import com.example.auth_service.dto.UserResponse;
import com.example.auth_service.entity.User;
import com.example.auth_service.payload.ApiResponse;
import com.example.auth_service.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 1️⃣ Register a new user
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> registerUser(@RequestBody User user) {
        UserResponse createdUser = userService.registerUser(user);
        return ResponseEntity.ok(new ApiResponse<>("User registered successfully", createdUser));
    }

    // 2️⃣ Sign in a user
    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<UserResponse>> signInUser(@RequestBody User user) {
        UserResponse retrievedUser = userService.signInUser(user.getPhone(), user.getPassword());
        if (retrievedUser != null ) {
            return ResponseEntity.ok(new ApiResponse<>("User signed in successfully", retrievedUser));
        } else {
            return ResponseEntity.status(401)
                    .body(new ApiResponse<>("Invalid email or password", null));
        }
    }

    // 3️⃣ Update user details
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        Optional<User> updatedUser = userService.updateUser(id, userDetails);
        return updatedUser
                .map(user -> ResponseEntity.ok(new ApiResponse<>("User updated successfully", user)))
                .orElse(ResponseEntity.status(404)
                        .body(new ApiResponse<>("User not found", null)));
    }

    // 4️⃣ Delete a user

}
