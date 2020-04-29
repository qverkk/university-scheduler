package com.kul.database.usermanagement.domain.exceptions;

public class NoSuchUserException extends Exception {
    public NoSuchUserException(String username) {
        super(String.format("User %s cannot be found.", username));
    }
}