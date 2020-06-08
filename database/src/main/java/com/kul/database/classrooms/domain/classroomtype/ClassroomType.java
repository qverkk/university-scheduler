package com.kul.database.classrooms.domain.classroomtype;

import lombok.Value;

@Value
public class ClassroomType {
    Long id;
    String name;

    public static ClassroomType newForName(String name) {
        return new ClassroomType(null, name);
    }
}
