package com.kul.api.adapter.admin.classrooms;

import com.kul.api.domain.admin.classroomtypes.ClassroomTypes;
import lombok.Value;

import java.util.List;

@Value
public class AddClassroomResponse {
    Long id;
    String name;
    List<ClassroomTypes> classroomTypes;
    Integer classroomSize;
}
