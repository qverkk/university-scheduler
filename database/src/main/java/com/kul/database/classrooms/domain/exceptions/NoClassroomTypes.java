package com.kul.database.classrooms.domain.exceptions;

public class NoClassroomTypes extends RuntimeException {
    public NoClassroomTypes() {
        super("There are no types in the database. Please add some");
    }
}
