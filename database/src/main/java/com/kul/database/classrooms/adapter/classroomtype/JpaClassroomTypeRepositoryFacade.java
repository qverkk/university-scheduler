package com.kul.database.classrooms.adapter.classroomtype;

import com.kul.database.classrooms.domain.classroomtype.ClassroomType;
import com.kul.database.classrooms.domain.classroomtype.ClassroomTypeRepository;
import com.kul.database.classrooms.domain.exceptions.ClassroomTypeAlreadyExists;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaClassroomTypeRepositoryFacade implements ClassroomTypeRepository {

    private final JpaClassroomTypeRepository classroomRepository;

    public JpaClassroomTypeRepositoryFacade(JpaClassroomTypeRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    @Override
    public ClassroomType save(ClassroomType classroomType) {
        boolean dbEntityIsPresent = classroomRepository.findByName(classroomType.getName()).isPresent();
        if (dbEntityIsPresent) {
            throw new ClassroomTypeAlreadyExists(classroomType.getName());
        }
        ClassroomTypeEntity savedEntity = classroomRepository.save(ClassroomTypeEntityMapper.fromDomain(classroomType));
        return ClassroomTypeEntityMapper.toDomain(savedEntity);
    }

    @Override
    public List<ClassroomType> findAll() {
        return classroomRepository.findAll().stream()
                .map(ClassroomTypeEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassroomType> findAllByNames(List<String> names) {
        return classroomRepository.findAllByNames(names).stream()
                .map(ClassroomTypeEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ClassroomType> findByName(String name) {
        return classroomRepository.findByName(name)
                .map(ClassroomTypeEntityMapper::toDomain);
    }

    @Override
    public void delete(Long id) {
        classroomRepository.deleteById(id);
    }
}
