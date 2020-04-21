package com.kul.database.service;

import com.kul.database.model.User;

import java.util.List;

public interface UserService {
    void enableUser(Long id);
    void disableUser(Long id);
    void deleteUser(Long id);
    Boolean isUserEnabled(Long id);
    User getUserById(Long id);
    User getUserByUsername(String username);
    List<User> getAllUsers();
}
