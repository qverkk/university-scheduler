package com.kul.database.classrooms.adapter.classroom;

import com.kul.database.classrooms.adapter.classroomtype.ClassroomTypeEntity;
import com.kul.database.classrooms.adapter.classroomtype.ClassroomTypeEntityMapper;
import com.kul.database.classrooms.adapter.classroomtype.JpaClassroomTypeRepository;
import com.kul.database.classrooms.domain.classroom.Classroom;
import com.kul.database.classrooms.domain.classroom.ClassroomsRepository;
import com.kul.database.classrooms.domain.classroomtype.ClassroomType;
import com.kul.database.classrooms.domain.exceptions.ClassroomTypeDoesntExist;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaClassroomRepositoryFacade implements ClassroomsRepository {

    private final JpaClassroomRepository classroomRepository;
    private final JpaClassroomTypeRepository classroomTypeRepository;

    public JpaClassroomRepositoryFacade(JpaClassroomRepository classroomRepository, JpaClassroomTypeRepository classroomTypeRepository) {
        this.classroomRepository = classroomRepository;
        this.classroomTypeRepository = classroomTypeRepository;
    }

    @Override
    public Classroom addOrUpdate(Classroom classroom) {
        ClassroomTypeEntity classroomType = classroomTypeRepository.findByName(classroom.getClassroomType().getName())
                .orElseThrow(() -> new ClassroomTypeDoesntExist(classroom.getClassroomType().getName()));

        ClassroomEntity classroomEntity = classroomRepository.findByName(classroom.getName())
                .orElseGet(() -> ClassroomEntity.newForName(classroom.getName()));

        classroomEntity.setClassroomTypeEntity(classroomType);

        return ClassroomEntityMapper.toDomain(
                classroomRepository.save(classroomEntity)
        );
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
    public Optional<Classroom> findByNameAndType(String name, ClassroomType type) {
        return classroomRepository.findByClassroomTypeEntityAndName(
                ClassroomTypeEntityMapper.fromDomain(type),
                name
        ).map(ClassroomEntityMapper::toDomain);
    }

    @Override
    public void delete(String name, ClassroomType classroomType) {
        classroomRepository.deleteByClassroomTypeEntityAndName(
                ClassroomTypeEntityMapper.fromDomain(classroomType),
                name
        );
    }
}
