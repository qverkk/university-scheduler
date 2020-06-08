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

    public void addNewClassroomType(String classroomTypeName) {
        classroomTypesRepository.addNewClassroomType(classroomTypeName);
    }

    public void addNewClassroom(Classrooms newClassroom) {
        classroomTypesRepository.addNewClassroom(newClassroom);
    }

    public List<Classrooms> getAllClassrooms() {
        return classroomTypesRepository.getAllClassrooms();
    }

    public void removeClassroomById(long id) {
        classroomTypesRepository.removeClassroomById(id);
    }
}
