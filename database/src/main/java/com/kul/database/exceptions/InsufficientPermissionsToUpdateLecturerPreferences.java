package com.kul.database.exceptions;

public class InsufficientPermissionsToUpdateLecturerPreferences extends Exception {
    public InsufficientPermissionsToUpdateLecturerPreferences(String response) {
        super(response);
    }
}
