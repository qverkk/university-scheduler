package com.kul.database.lecturerpreferences.domain.exceptions;

public class LecturerPreferenceAlreadyExists extends Exception {
    public LecturerPreferenceAlreadyExists(String reason) {
        super(reason);
    }
}
