package com.kul.database.classrooms.api.model.classroom;

import com.kul.database.classrooms.domain.classroom.Classroom;
import com.kul.database.classrooms.domain.classroomtype.ClassroomType;

public class AddOrUpdateClassroomMapper {

    public static Classroom toDomain(AddOrUpdateClassroomRequest request) {
        return new Classroom(
                null,
                request.getName(),
                ClassroomType.newForName(request.getClassroomType())
        );
    }

    public static AddOrUpdateClassroomResponse fromDomain(Classroom classroom) {
        return new AddOrUpdateClassroomResponse(
                classroom.getId(),
                classroom.getName(),
                classroom.getClassroomType()
        );
    }
}
