package com.kul.database.classrooms.api.model.classroom;

import com.kul.database.classrooms.api.model.classroomtype.FetchClassroomTypesResponse;
import com.kul.database.classrooms.domain.classroom.Classroom;

import java.util.stream.Collectors;

public class FetchClassroomMapper {
    public static FetchClassroomResponse fromDomain(Classroom classroom) {
        return new FetchClassroomResponse(
                classroom.getId(),
                classroom.getName(),
                classroom.getClassroomTypes().stream()
                        .map(c -> new FetchClassroomTypesResponse(
                                c.getId(),
                                c.getName()
                        )).collect(Collectors.toList()),
                classroom.getClassroomSize()
        );
    }
}
