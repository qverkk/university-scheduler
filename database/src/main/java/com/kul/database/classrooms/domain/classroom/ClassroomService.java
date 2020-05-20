package com.kul.database.classrooms.domain.classroom;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomService {

    private final ClassroomsRepository classroomsRepository;

    public ClassroomService(ClassroomsRepository classroomsRepository) {
        this.classroomsRepository = classroomsRepository;
    }

    public List<Classroom> findAll() {
        return classroomsRepository.findAll();
    }

    public Classroom addOrUpdate(Classroom classroom) {
        return classroomsRepository.addOrUpdate(classroom);
    }

    public void delete(Long id) {
        classroomsRepository.delete(id);
    }
}
