package com.kul.database.lecturerlessons.domain.exceptions;

public class LessonTypeAlreadyExists extends RuntimeException {
    public LessonTypeAlreadyExists(String msg) {
        super(msg);
    }
}
