package com.apapeasy.stockguard.service;

import com.apapeasy.stockguard.model.User;
import com.apapeasy.stockguard.repository.UserDb;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDb userDb;

    public void registerUser(User user) {
        userDb.save(user);
    }

    public Boolean existsByUsername(String username) {
        return userDb.existsByUsername(username);
    }

    public Boolean existsByEmail(String email) {
        return userDb.existsByEmail(email);
    }

    public List<User> getAllUsers() {
        return userDb.findAll();
    }

    public User updateUser(User user) {
        userDb.save(user);
        return user;
    }

    public void deleteUser(User user) {
        userDb.delete(user);
    }
}
