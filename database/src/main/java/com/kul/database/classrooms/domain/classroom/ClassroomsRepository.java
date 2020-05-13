package com.kul.database.classrooms.domain.classroom;

import com.kul.database.classrooms.domain.classroomtype.ClassroomType;

import java.util.List;

public interface ClassroomsRepository {
    Classroom addOrUpdate(Classroom classroom);

    List<Classroom> findAll();

    List<Classroom> findAllForClassroom(String name);

    void delete(String name, ClassroomType classroomType);
}
