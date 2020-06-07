package com.kul.api.adapter.admin.classrooms;

import lombok.Value;

import java.util.List;

@Value
public class AddClassroom {
    String name;
    List<String> classroomTypes;
    Integer classroomSize;
}
