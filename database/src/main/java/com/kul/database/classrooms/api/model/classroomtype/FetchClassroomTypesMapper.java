package com.kul.database.classrooms.api.model.classroomtype;

import com.kul.database.classrooms.domain.classroomtype.ClassroomType;

public class FetchClassroomTypesMapper {
    public static FetchClassroomTypesResponse fromDomain(ClassroomType classroomType) {
        return new FetchClassroomTypesResponse(
                classroomType.getId(),
                classroomType.getName()
        );
    }
}
