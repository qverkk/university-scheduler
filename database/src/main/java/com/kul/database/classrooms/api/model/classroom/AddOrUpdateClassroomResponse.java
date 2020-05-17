package com.kul.database.classrooms.api.model.classroom;

import com.kul.database.classrooms.domain.classroomtype.ClassroomType;
import lombok.Value;

@Value
public class AddOrUpdateClassroomResponse {
    Long id;
    String name;
    ClassroomType classroomType;
}
