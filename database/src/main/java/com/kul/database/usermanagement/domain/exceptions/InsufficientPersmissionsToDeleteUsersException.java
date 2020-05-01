package com.kul.database.usermanagement.domain.exceptions;

import com.kul.database.usermanagement.domain.User;

public class InsufficientPersmissionsToDeleteUsersException extends RuntimeException {
    public InsufficientPersmissionsToDeleteUsersException(User user) {
        super(String.format("User %s doesn't have enough permissions to delete users", user.getUsername()));
    }
}
