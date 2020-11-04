package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User updateUser(User user);
    List<User> getAllUsers();
    User getUserByUsername(String email);
    void deleteUserByUsername(String email);
    boolean existsByUsername(String email);
}
