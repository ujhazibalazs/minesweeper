package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User updateUser(User user);
    List<User> getAllUsers();
    User getUserByUsername(String username);
    void deleteUserByUsername(String username);
    boolean existsByUsername(String username);
}
