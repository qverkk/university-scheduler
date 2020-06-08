package com.kul.database.classrooms.api.model.classroomtype;

import com.kul.database.classrooms.domain.classroomtype.ClassroomType;

public class AddClassroomTypeMapper {

    public static ClassroomType toDomain(AddClassroomTypeRequest request) {
        return ClassroomType.newForName(request.getName());
    }

    public static AddClassroomTypeResponse fromDomain(ClassroomType classroomType) {
        return new AddClassroomTypeResponse(
                classroomType.getId(),
                classroomType.getName()
        );
    }
}
