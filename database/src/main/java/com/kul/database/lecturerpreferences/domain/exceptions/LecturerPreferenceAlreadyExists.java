package com.kul.database.lecturerpreferences.domain.exceptions;

public class LecturerPreferenceAlreadyExists extends RuntimeException {
    public LecturerPreferenceAlreadyExists(String reason) {
        super(reason);
    }
}
