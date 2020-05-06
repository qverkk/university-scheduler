package com.kul.database.lecturerlessons.domain.exceptions;

public class NoSuchLecturerLesson extends RuntimeException {
    public NoSuchLecturerLesson(String message) {
        super(message);
    }
}
