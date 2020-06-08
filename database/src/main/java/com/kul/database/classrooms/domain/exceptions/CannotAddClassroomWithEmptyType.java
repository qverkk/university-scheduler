package com.kul.database.classrooms.domain.exceptions;

public class CannotAddClassroomWithEmptyType extends RuntimeException {
    public CannotAddClassroomWithEmptyType() {
        super("Cannot add a class with no types assigned to it");
    }
}
