package com.kul.database.lecturerpreferences.domain.exceptions;

public class LecturerPreferenceDoesntExist extends RuntimeException {
    public LecturerPreferenceDoesntExist(String response) {
        super(response);
    }
}
