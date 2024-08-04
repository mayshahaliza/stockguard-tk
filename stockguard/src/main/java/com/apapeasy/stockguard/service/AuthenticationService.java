package com.apapeasy.stockguard.service;

import com.apapeasy.stockguard.model.User;

public interface AuthenticationService {
    public void addLoggedInUser(User user);
    public User getLoggedInUser();
    public void removeLoggedInUser();
}