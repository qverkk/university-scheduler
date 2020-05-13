package com.kul.database.classrooms.adapter.classroom;

import com.kul.database.classrooms.adapter.classroomtype.ClassroomTypeEntityMapper;
import com.kul.database.classrooms.domain.classroom.Classroom;
import com.kul.database.classrooms.domain.classroom.ClassroomsRepository;
import com.kul.database.classrooms.domain.classroomtype.ClassroomType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JpaClassroomRepositoryFacade implements ClassroomsRepository {

    private final JpaClassroomRepository classroomRepository;

    public JpaClassroomRepositoryFacade(JpaClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    @Override
    public Classroom addOrUpdate(Classroom classroom) {
        return null;
    }

    @Override
    public List<Classroom> findAll() {
        return classroomRepository.findAll().stream()
                .map(ClassroomEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Classroom> findAllForClassroom(String name) {
        return classroomRepository.findAllByName(name).stream()
                .map(ClassroomEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String name, ClassroomType classroomType) {
        classroomRepository.deleteByClassroomTypeEntityAndName(
                ClassroomTypeEntityMapper.fromDomain(classroomType),
                name
        );
    }
}
