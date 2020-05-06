package com.kul.database.usermanagement.domain.exceptions;

import com.kul.database.usermanagement.domain.User;

public class InsufficientPersmissionsToEnableUsersException extends RuntimeException {
    public InsufficientPersmissionsToEnableUsersException(User user) {
        super(String.format("User %s doesn't have enough permissions to enable/disable users", user.getUsername()));
    }
}
