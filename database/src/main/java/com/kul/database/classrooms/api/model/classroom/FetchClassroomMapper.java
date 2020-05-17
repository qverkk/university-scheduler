package com.kul.database.classrooms.api.model.classroom;

import com.kul.database.classrooms.api.model.classroomtype.FetchClassroomTypesResponse;
import com.kul.database.classrooms.domain.classroom.Classroom;

public class FetchClassroomMapper {
    public static FetchClassroomResponse fromDomain(Classroom classroom) {
        return new FetchClassroomResponse(
                classroom.getId(),
                classroom.getName(),
                new FetchClassroomTypesResponse(
                        classroom.getClassroomType().getId(),
                        classroom.getClassroomType().getName()
                )
        );
    }
}
