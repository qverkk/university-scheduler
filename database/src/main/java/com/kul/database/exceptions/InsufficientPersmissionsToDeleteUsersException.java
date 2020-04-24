package com.kul.database.exceptions;

import com.kul.database.model.User;

public class InsufficientPersmissionsToDeleteUsersException extends Exception {
    public InsufficientPersmissionsToDeleteUsersException(User user) {
        super(String.format("User %s doesn't have enough permissions to delete users", user.getUsername()));
    }
}
