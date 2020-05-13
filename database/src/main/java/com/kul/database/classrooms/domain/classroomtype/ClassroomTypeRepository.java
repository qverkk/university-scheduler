package com.kul.database.classrooms.domain.classroomtype;

import java.util.List;

public interface ClassroomTypeRepository {
    ClassroomType save(ClassroomType classroomType);
    List<ClassroomType> findAll();
}
