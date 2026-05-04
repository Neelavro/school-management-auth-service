package com.example.auth_service.service.impl;


import com.example.auth_service.dto.UserResponse;
import com.example.auth_service.entity.User;
import com.example.auth_service.repository.UserRepository;
import com.example.auth_service.service.UserService;
import com.example.auth_service.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private  final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil){

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserResponse registerUser(User user){
        if(userRepository.existsByPhone(user.getPhone())){
            throw new RuntimeException("Email already exists");
        }
        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );
        userRepository.save(user);
        return new UserResponse(user);
    }
    @Override
    public Optional<User> updateUser(Long id, User userDetails) {
        return userRepository.findById(id).map(existing -> {
            if (userDetails.getPhone() != null)
                existing.setPhone(userDetails.getPhone());
            if (userDetails.getPassword() != null)
                existing.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            if (userDetails.getRole() != null)
                existing.setRole(userDetails.getRole());
            if (userDetails.getIsActive() != null)
                existing.setIsActive(userDetails.getIsActive());
            return userRepository.save(existing);
        });
    }

    @Override
    public UserResponse signInUser(String phone, String password){
        Optional<User> user = userRepository.findByPhone(phone);
        if(user.isPresent()){
            User user1  = user.get();
            if(passwordEncoder.matches(password, user1.getPassword())){
                String token = jwtUtil.generateToken(user1);
                UserResponse userResponse = new UserResponse(
                        user1.getId(),
                        user1.getPhone(),
                        user1.getRole(),
                        token
                );
                return  userResponse;
            }
            else{
                return null;
            }
        }
        else{
            return null;
        }

    }

}
