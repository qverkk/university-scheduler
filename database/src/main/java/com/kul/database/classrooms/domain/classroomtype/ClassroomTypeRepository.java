package com.kul.database.classrooms.domain.classroomtype;

import java.util.List;
import java.util.Optional;

public interface ClassroomTypeRepository {
    ClassroomType save(ClassroomType classroomType);

    List<ClassroomType> findAll();

    Optional<ClassroomType> findByName(String name);

    void delete(Long id);
}
