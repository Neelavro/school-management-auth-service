package com.example.auth_service.service.impl;


import com.example.auth_service.entity.User;
import com.example.auth_service.repository.UserRepository;
import com.example.auth_service.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private  final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(User user){
        if(userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email already exists");
        }

        return userRepository.save(user);
    }
    @Override
    public Optional<User> updateUser(Long id,User userDetails){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            userRepository.save(userDetails);
            return  user;
        }
        else{
            return Optional.empty();
        }

    }

    @Override
    public User signInUser(String email, String password){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            User user1  = user.get();
            if(user1.getEmail().equals(email) && user1.getPassword().equals(password)){
                return  user1;
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
