package com.kul.database.classrooms.domain.exceptions;

public class ClassroomTypeAlreadyExists extends RuntimeException {
    public ClassroomTypeAlreadyExists(String name) {
        super(String.format("Classroom type for %s already exists", name));
    }
}
