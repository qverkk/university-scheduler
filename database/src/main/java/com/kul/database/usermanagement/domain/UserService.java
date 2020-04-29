package com.kul.database.usermanagement.domain;

import com.kul.database.usermanagement.domain.exceptions.InsufficientPersmissionsToDeleteUsersException;
import com.kul.database.usermanagement.domain.exceptions.InsufficientPersmissionsToEnableUsersException;
import com.kul.database.usermanagement.domain.exceptions.InsufficientPersmissionsToGetAllUserData;
import com.kul.database.usermanagement.domain.exceptions.NoSuchUserException;

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
