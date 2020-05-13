package com.kul.database.lecturerlessons.domain.exceptions;

public class InsufficientPermissionsToUpdateLesson extends RuntimeException {
    public InsufficientPermissionsToUpdateLesson(String message) {
        super(message);
    }
}
