package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.tinylog.Logger;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        Optional<User> userDb = this.userRepository.findById(user.getUsername());

        if(userDb.isEmpty()) {
            return userRepository.save(user);
        } else {
            Logger.error("Email already in use.");
            throw new RuntimeException();
            //throw email already in use exception
        }
    }

    @Override
    public User updateUser(User user) {
        Optional<User> userDb = this.userRepository.findById(user.getUsername());
        
        if(userDb.isPresent()) {
            User userUpdate = userDb.get();
            userUpdate.setUsername(user.getUsername());
            userUpdate.setPassword(user.getPassword());
            userUpdate.setEnabled(user.isEnabled());
            userRepository.save(userUpdate);
            return userUpdate;
        } else {
            Logger.error("Record not found");
            throw new RuntimeException();
            //throw record not found exception
        }
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<User> userDb = this.userRepository.findById(username);
        if(userDb.isPresent()) {
            return userDb.get();
        } else {
            Logger.error("Record with the given email not found");
            throw new RuntimeException();
            //throw record with this email not found exception.
        }
    }

    @Override
    public void deleteUserByUsername(String email) {
        Optional<User> userDb = this.userRepository.findById(email);
        if(userDb.isPresent()) {
            this.userRepository.delete(userDb.get());
        } else {
            Logger.error("Record with the given email not found");
            throw new RuntimeException();
            //throw record with this email not found exception.
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsById(username);
    }

}
