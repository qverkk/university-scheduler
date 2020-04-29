package com.kul.database.lecturerpreferences.domain.exceptions;

public class LecturerPreferenceDoesntExist extends Exception {
    public LecturerPreferenceDoesntExist(String response) {
        super(response);
    }
}
