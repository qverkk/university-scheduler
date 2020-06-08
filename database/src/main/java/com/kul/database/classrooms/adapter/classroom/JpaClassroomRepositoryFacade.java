package com.kul.database.classrooms.adapter.classroom;

import com.kul.database.classrooms.adapter.classroomtype.ClassroomTypeEntity;
import com.kul.database.classrooms.adapter.classroomtype.ClassroomTypeEntityMapper;
import com.kul.database.classrooms.adapter.classroomtype.JpaClassroomTypeRepository;
import com.kul.database.classrooms.domain.classroom.Classroom;
import com.kul.database.classrooms.domain.classroom.ClassroomsRepository;
import com.kul.database.classrooms.domain.classroomtype.ClassroomType;
import com.kul.database.classrooms.domain.exceptions.CannotAddClassroomWithEmptyType;
import com.kul.database.classrooms.domain.exceptions.ClassroomTypeDoesntExist;
import com.kul.database.classrooms.domain.exceptions.NoClassroomTypes;
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
        if (classroom.getClassroomTypes().isEmpty()) {
            throw new CannotAddClassroomWithEmptyType();
        }
        List<String> initialClassroomTypeNames = classroom.getClassroomTypes().stream()
                .map(ClassroomType::getName)
                .collect(Collectors.toList());
        List<ClassroomTypeEntity> classroomTypeEntities = classroomTypeRepository.findAllByNames(
                initialClassroomTypeNames);

        if (classroomTypeEntities.isEmpty()) {
            throw new NoClassroomTypes();
        }

        List<String> classroomTypeEntitiesNames = classroomTypeEntities.stream()
                .map(ClassroomTypeEntity::getName)
                .collect(Collectors.toList());
        if (!initialClassroomTypeNames.containsAll(classroomTypeEntitiesNames)) {
            initialClassroomTypeNames.removeAll(classroomTypeEntitiesNames);
            String missingTypes = initialClassroomTypeNames.stream()
                    .map(e -> e + " ")
                    .toString();
            throw new ClassroomTypeDoesntExist(missingTypes);
        }

//        ClassroomTypeEntity classroomType = classroomTypeRepository.findByName(classroom.getClassroomType().getName())
//                .orElseThrow(() -> new ClassroomTypeDoesntExist(classroom.getClassroomType().getName()));

        ClassroomEntity classroomEntity = classroomRepository.findByName(classroom.getName())
                .orElseGet(() -> ClassroomEntity.newForName(classroom.getName()));

        classroomEntity.setClassroomTypeEntity(classroomTypeEntities);
        classroomEntity.setClassroomSize(classroom.getClassroomSize());

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
    public Optional<Classroom> findByNameAndTypes(String name, List<ClassroomType> types) {
        if (types.isEmpty()) {
            return Optional.empty();
        }
        return classroomRepository.findByClassroomTypeEntityAndName(
                types.stream()
                        .map(ClassroomTypeEntityMapper::fromDomain)
                        .collect(Collectors.toList()),
                name
        ).map(ClassroomEntityMapper::toDomain);
    }

    @Override
    public void delete(String name, List<ClassroomType> classroomType) {
        classroomRepository.deleteByClassroomTypeEntityAndName(
                classroomType.stream()
                    .map(ClassroomTypeEntityMapper::fromDomain)
                    .collect(Collectors.toList()),
                name
        );
    }

    @Override
    public void delete(Long id) {
        classroomRepository.deleteById(id);
    }

    @Override
    public Optional<Classroom> findById(Long id) {
        return classroomRepository.findById(id).map(ClassroomEntityMapper::toDomain);
    }
}
