package com.kul.database.lecturerpreferences.domain.exceptions;

public class InsufficientPermissionsToUpdateLecturerPreferences extends RuntimeException {
    public InsufficientPermissionsToUpdateLecturerPreferences(String response) {
        super(response);
    }
}
