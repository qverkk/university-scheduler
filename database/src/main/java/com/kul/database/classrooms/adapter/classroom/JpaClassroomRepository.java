package com.kul.database.classrooms.adapter.classroom;

import com.kul.database.classrooms.adapter.classroomtype.ClassroomTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaClassroomRepository extends JpaRepository<ClassroomEntity, Long> {
    List<ClassroomEntity> findAllByName(String name);

    Optional<ClassroomEntity> findByClassroomTypeEntityAndName(ClassroomTypeEntity entity, String name);

    Optional<ClassroomEntity> findByName(String name);

    void deleteByClassroomTypeEntityAndName(ClassroomTypeEntity classroomtype, String name);
}
