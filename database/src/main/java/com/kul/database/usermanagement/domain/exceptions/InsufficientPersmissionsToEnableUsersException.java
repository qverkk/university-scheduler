package com.kul.database.usermanagement.domain.exceptions;

public class InsufficientPersmissionsToEnableUsersException extends RuntimeException {
    public InsufficientPersmissionsToEnableUsersException(String username) {
        super(String.format("User %s doesn't have enough permissions to enable/disable users", username));
    }
}
