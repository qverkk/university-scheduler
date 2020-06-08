package com.kul.database.classrooms.domain.exceptions;

public class ClassroomAlreadyExists extends RuntimeException {
    public ClassroomAlreadyExists(String name) {
        super(String.format("Classroom %s already exists", name));
    }
}
