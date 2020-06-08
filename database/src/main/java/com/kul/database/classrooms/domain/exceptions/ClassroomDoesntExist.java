package com.kul.database.classrooms.domain.exceptions;

public class ClassroomDoesntExist extends RuntimeException {
    public ClassroomDoesntExist(String name) {
        super(String.format("Classroom %s doesnt exist", name));
    }
}
