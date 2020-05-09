package com.kul.database.lecturerlessons.domain.exceptions;

public class NoSuchLessonType extends RuntimeException {
    public NoSuchLessonType(String msg) {
        super(msg);
    }
}
