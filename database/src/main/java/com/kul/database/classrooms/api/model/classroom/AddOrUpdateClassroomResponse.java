package com.kul.database.classrooms.api.model.classroom;

import com.kul.database.classrooms.domain.classroomtype.ClassroomType;
import lombok.Value;

import java.util.List;

@Value
public class AddOrUpdateClassroomResponse {
    Long id;
    String name;
    List<ClassroomType> classroomTypes;
    Integer classroomSize;
}
