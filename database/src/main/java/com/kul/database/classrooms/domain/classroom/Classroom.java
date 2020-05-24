package com.kul.database.classrooms.domain.classroom;

import com.kul.database.classrooms.domain.classroomtype.ClassroomType;
import lombok.Value;

import java.util.Collections;
import java.util.List;

@Value
public class Classroom {
    Long id;
    String name;
    List<ClassroomType> classroomTypes;
    Integer classroomSize;

    public static Classroom newForName(String classroomName) {
        return new Classroom(
                null,
                classroomName,
                Collections.emptyList(),
                0
        );
    }
}
