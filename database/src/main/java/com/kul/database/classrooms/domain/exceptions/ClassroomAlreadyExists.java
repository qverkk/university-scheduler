package com.kul.database.classrooms.domain.exceptions;

public class ClassroomAlreadyExists extends RuntimeException {
    public ClassroomAlreadyExists(String name) {
        super(name);
    }
}
