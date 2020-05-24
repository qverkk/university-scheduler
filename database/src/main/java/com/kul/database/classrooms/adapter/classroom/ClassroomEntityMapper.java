package com.kul.database.classrooms.adapter.classroom;

import com.kul.database.classrooms.adapter.classroomtype.ClassroomTypeEntityMapper;
import com.kul.database.classrooms.domain.classroom.Classroom;

import java.util.stream.Collectors;

public class ClassroomEntityMapper {
    public static Classroom toDomain(ClassroomEntity classroomEntity) {
        return new Classroom(
                classroomEntity.getId(),
                classroomEntity.getName(),
                classroomEntity.getClassroomTypeEntity().stream()
                        .map(ClassroomTypeEntityMapper::toDomain)
                        .collect(Collectors.toList()),
                classroomEntity.getClassroomSize()
        );
    }

    public static ClassroomEntity fromDomain(Classroom classroom) {
        return new ClassroomEntity(
                classroom.getId(),
                classroom.getName(),
                classroom.getClassroomTypes().stream()
                        .map(ClassroomTypeEntityMapper::fromDomain)
                        .collect(Collectors.toList()),
                classroom.getClassroomSize()
        );
    }
}
