package com.kul.database.classrooms.domain.exceptions;

public class ClassroomTypeDoesntExist extends RuntimeException {
    public ClassroomTypeDoesntExist(String name) {
        super(String.format("Class room type of %s does not exist", name));
    }
}
