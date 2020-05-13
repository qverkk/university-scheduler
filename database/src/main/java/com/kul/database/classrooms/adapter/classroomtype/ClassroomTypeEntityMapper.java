package com.kul.database.classrooms.adapter.classroomtype;

import com.kul.database.classrooms.domain.classroomtype.ClassroomType;

public class ClassroomTypeEntityMapper {
    public static ClassroomType toDomain(ClassroomTypeEntity classroomTypeEntity) {
        return new ClassroomType(
                classroomTypeEntity.getId(),
                classroomTypeEntity.getName()
        );
    }

    public static ClassroomTypeEntity fromDomain(ClassroomType classroomType) {
        return new ClassroomTypeEntity(
                classroomType.getId(),
                classroomType.getName()
        );
    }
}
