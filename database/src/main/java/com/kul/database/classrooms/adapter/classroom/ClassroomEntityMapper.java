package com.kul.database.classrooms.adapter.classroom;

import com.kul.database.classrooms.adapter.classroomtype.ClassroomTypeEntityMapper;
import com.kul.database.classrooms.domain.classroom.Classroom;

public class ClassroomEntityMapper {
    public static Classroom toDomain(ClassroomEntity classroomEntity) {
        return new Classroom(
                classroomEntity.getId(),
                classroomEntity.getName(),
                ClassroomTypeEntityMapper.toDomain(classroomEntity.getClassroomTypeEntity())
        );
    }

    public static ClassroomEntity fromDomain(Classroom classroom) {
        return new ClassroomEntity(
                classroom.getId(),
                classroom.getName(),
                ClassroomTypeEntityMapper.fromDomain(classroom.getClassroomType())
        );
    }
}
