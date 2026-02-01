package com.example.auth_service.service;

import com.example.auth_service.entity.User;

public interface UserService {

    User registerUser(User user);

    User updateUser(User user);

    User getUser(User user);

}
