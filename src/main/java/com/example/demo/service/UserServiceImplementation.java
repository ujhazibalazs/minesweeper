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
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        Optional<User> userDb = this.userRepository.findById(user.getEmail());
        
        if(userDb.isPresent()) {
            User userUpdate = userDb.get();
            userUpdate.setEmail(user.getEmail());
            userUpdate.setUsername(user.getUsername());
            userUpdate.setPassword(user.getPassword());
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
    public User getUserByEmail(String email) {
        Optional<User> userDb = this.userRepository.findById(email);
        if(userDb.isPresent()) {
            return userDb.get();
        } else {
            Logger.error("Record with the given email not found");
            throw new RuntimeException();
            //throw record with this email not found exception.
        }
    }

    @Override
    public void deleteUserByEmail(String email) {
        Optional<User> userDb = this.userRepository.findById(email);
        if(userDb.isPresent()) {
            this.userRepository.delete(userDb.get());
        } else {
            Logger.error("Recod with the given email not found");
            throw new RuntimeException();
            //throw record with this email not found exception.
        }
    }
}
