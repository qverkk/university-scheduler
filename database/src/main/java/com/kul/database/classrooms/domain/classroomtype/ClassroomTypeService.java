package com.kul.database.classrooms.domain.classroomtype;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomTypeService {
    private final ClassroomTypeRepository classroomTypeRepository;

    public ClassroomTypeService(ClassroomTypeRepository classroomTypeRepository) {
        this.classroomTypeRepository = classroomTypeRepository;
    }

    public ClassroomType save(ClassroomType classroomType) {
        return classroomTypeRepository.save(classroomType);
    }

    public List<ClassroomType> findAll() {
        return classroomTypeRepository.findAll();
    }

    public void delete(Long id) {
        classroomTypeRepository.delete(id);
    }
}
