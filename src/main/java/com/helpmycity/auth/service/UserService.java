package com.helpmycity.auth.service;


import com.helpmycity.auth.model.User;

public interface UserService {
    public User findUserByEmail(String email);
    public void saveUser(User user);
}
