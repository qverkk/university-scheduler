package com.kul.database.classrooms.domain.classroom;

import com.kul.database.classrooms.domain.classroomtype.ClassroomType;
import lombok.Value;

@Value
public class Classroom {
    Long id;
    String name;
    ClassroomType classroomType;
}
