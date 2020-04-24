package com.kul.database.exceptions;

import com.kul.database.model.User;

public class InsufficientPersmissionsToEnableUsersException extends Exception {
    public InsufficientPersmissionsToEnableUsersException(User user) {
        super(String.format("User %s doesn't have enough permissions to enable/disable users", user.getUsername()));
    }
}
