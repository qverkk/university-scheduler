package com.kul.api.domain.admin.classroomtypes;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ClassroomTypesManagement {
    private final ClassroomTypesRepository classroomTypesRepository;

    public List<ClassroomTypes> getAllClassroomTypes() {
        return classroomTypesRepository.getAllClassroomTypes();
    }

    public void removeClassroomTypeById(Long id) {
        classroomTypesRepository.removeClassroomTypeById(id);
    }
}
