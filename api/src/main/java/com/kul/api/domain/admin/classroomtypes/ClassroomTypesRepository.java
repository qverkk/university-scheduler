package com.kul.api.domain.admin.classroomtypes;

import java.util.List;

public interface ClassroomTypesRepository {
    List<ClassroomTypes> getAllClassroomTypes();

    void removeClassroomTypeById(Long id);

    void addNewClassroomType(String classroomTypeName);

    void addNewClassroom(Classrooms newClassroom);

    List<Classrooms> getAllClassrooms();
}
