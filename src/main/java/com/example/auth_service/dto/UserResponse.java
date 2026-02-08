package com.example.auth_service.dto;

import com.example.auth_service.entity.Role;
import com.example.auth_service.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    private Long id;
    private String email;
    private Role role; // optional: if you have roles
    private String accessToken; // optional: user full name

    public UserResponse(Long id, String email, Role role, String accessToken) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.accessToken = accessToken;
    }

    // Convenience constructor to build from your User entity
    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getPhone();
        this.role = user.getRole();
    }

}
