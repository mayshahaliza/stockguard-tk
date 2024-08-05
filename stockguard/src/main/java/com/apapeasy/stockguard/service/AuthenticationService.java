package com.apapeasy.stockguard.service;

import com.apapeasy.stockguard.model.User;

public interface AuthenticationService {
    void addLoggedInUser(User user);
    User getLoggedInUser();
    void removeLoggedInUser();
}