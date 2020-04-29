package com.kul.database.usermanagement.domain.exceptions;

public class InsufficientPersmissionsToGetAllUserData extends Exception {
    public InsufficientPersmissionsToGetAllUserData(String username) {
        super(String.format("User %s can't get all users sensitive data", username));
    }
}
