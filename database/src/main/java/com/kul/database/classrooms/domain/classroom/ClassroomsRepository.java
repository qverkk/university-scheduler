package com.kul.database.classrooms.domain.classroom;

import com.kul.database.classrooms.domain.classroomtype.ClassroomType;

import java.util.List;
import java.util.Optional;

public interface ClassroomsRepository {
    Classroom addOrUpdate(Classroom classroom);

    List<Classroom> findAll();

    List<Classroom> findAllForClassroom(String name);

    Optional<Classroom> findByNameAndType(String name, ClassroomType type);

    void delete(String name, ClassroomType classroomType);
}
