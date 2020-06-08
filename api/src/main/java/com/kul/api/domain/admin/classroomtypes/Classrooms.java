package com.kul.api.domain.admin.classroomtypes;

import lombok.Value;

import java.util.List;

@Value
public class Classrooms {
    Long id;
    String name;
    List<String> classroomTypes;
    Integer classroomSize;
}
