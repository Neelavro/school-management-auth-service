package com.example.auth_service.service;

import com.example.auth_service.entity.User;

import java.util.Optional;

public interface UserService {

    User registerUser(User user);

    Optional<User> updateUser(Long id,User user);

    User signInUser(String email, String password);

}
