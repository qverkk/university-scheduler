package com.kul.database.lecturerlessons.domain.exceptions;

public class UserCannotHaveLessons extends RuntimeException {
    public UserCannotHaveLessons(String username) {
        super(String.format("User %s cannot have lessons", username));
    }
}
