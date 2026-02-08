package com.example.auth_service.service;

import com.example.auth_service.dto.UserResponse;
import com.example.auth_service.entity.User;

import java.util.Optional;

public interface UserService {

    UserResponse registerUser(User user);

    Optional<User> updateUser(Long id,User user);

    UserResponse signInUser(String email, String password);

}
