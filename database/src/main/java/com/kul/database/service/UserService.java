package com.kul.database.service;

public interface UserService {
    void enableUser(Long id);
    void deleteUser(Long id);
    Boolean isUserEnabled(Long id);
}
