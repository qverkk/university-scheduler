package com.kul.database.service;

import com.kul.database.model.User;

public interface UserService {
    void enableUser(Long id);
    void deleteUser(Long id);
    Boolean isUserEnabled(Long id);
    User getUserById(Long id);
}
