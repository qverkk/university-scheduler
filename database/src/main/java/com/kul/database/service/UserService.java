package com.kul.database.service;

import com.kul.database.exceptions.InsufficientPersmissionsToDeleteUsersException;
import com.kul.database.exceptions.InsufficientPersmissionsToEnableUsersException;
import com.kul.database.exceptions.InsufficientPersmissionsToGetAllUserData;
import com.kul.database.exceptions.NoSuchUserException;
import com.kul.database.model.User;

import java.util.List;

public interface UserService {
    void enableUser(Long id, String username) throws NoSuchUserException, InsufficientPersmissionsToEnableUsersException;
    void disableUser(Long id, String username) throws NoSuchUserException, InsufficientPersmissionsToEnableUsersException;
    void deleteUser(Long id, String username) throws NoSuchUserException, InsufficientPersmissionsToDeleteUsersException;
    Boolean isUserEnabled(Long id);
    User getUserById(Long id);
    User getUserByUsername(String username);
    List<User> getAllUsers(String username) throws NoSuchUserException, InsufficientPersmissionsToGetAllUserData;
}
