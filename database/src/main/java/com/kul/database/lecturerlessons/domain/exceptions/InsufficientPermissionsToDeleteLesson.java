package com.kul.database.lecturerlessons.domain.exceptions;

public class InsufficientPermissionsToDeleteLesson extends RuntimeException {
    public InsufficientPermissionsToDeleteLesson(String msg) {
        super(msg);
    }
}
