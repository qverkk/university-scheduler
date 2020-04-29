package com.kul.database.lecturerpreferences.domain.exceptions;

public class InsufficientPermissionsToUpdateLecturerPreferences extends Exception {
    public InsufficientPermissionsToUpdateLecturerPreferences(String response) {
        super(response);
    }
}
