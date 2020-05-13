package com.kul.database.classrooms.adapter.classroomtype;

import com.kul.database.classrooms.domain.classroomtype.ClassroomType;
import com.kul.database.classrooms.domain.classroomtype.ClassroomTypeRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JpaClassroomRepositoryFacade implements ClassroomTypeRepository {

    private final JpaClassroomTypeRepository classroomRepository;

    public JpaClassroomRepositoryFacade(JpaClassroomTypeRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    @Override
    public ClassroomType save(ClassroomType classroomType) {
        ClassroomTypeEntity savedEntity = classroomRepository.save(ClassroomTypeEntityMapper.fromDomain(classroomType));
        return ClassroomTypeEntityMapper.toDomain(savedEntity);
    }

    @Override
    public List<ClassroomType> findAll() {
        return classroomRepository.findAll().stream()
                .map(ClassroomTypeEntityMapper::toDomain)
                .collect(Collectors.toList());
    }
}
