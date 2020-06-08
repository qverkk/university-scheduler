package com.kul.database.classrooms.api.model.classroom;

import com.kul.database.classrooms.domain.classroom.Classroom;
import com.kul.database.classrooms.domain.classroomtype.ClassroomType;

import java.util.stream.Collectors;

public class AddOrUpdateClassroomMapper {

    public static Classroom toDomain(AddOrUpdateClassroomRequest request) {
        return new Classroom(
                null,
                request.getName(),
                request.getClassroomTypes().stream()
                    .map(ClassroomType::newForName)
                    .collect(Collectors.toList()),
                request.getClassroomSize()
        );
    }

    public static AddOrUpdateClassroomResponse fromDomain(Classroom classroom) {
        return new AddOrUpdateClassroomResponse(
                classroom.getId(),
                classroom.getName(),
                classroom.getClassroomTypes(),
                classroom.getClassroomSize()
        );
    }
}
