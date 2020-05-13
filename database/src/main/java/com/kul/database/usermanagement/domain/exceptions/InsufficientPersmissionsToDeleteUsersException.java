package com.kul.database.usermanagement.domain.exceptions;

public class InsufficientPersmissionsToDeleteUsersException extends RuntimeException {
    public InsufficientPersmissionsToDeleteUsersException(String username) {
        super(String.format("User %s doesn't have enough permissions to delete users", username));
    }
}
