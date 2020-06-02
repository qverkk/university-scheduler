package com.kul.api.domain.admin.classroomtypes;

import java.util.List;

public interface ClassroomTypesRepository {
    List<ClassroomTypes> getAllClassroomTypes();

    void removeClassroomTypeById(Long id);
}
