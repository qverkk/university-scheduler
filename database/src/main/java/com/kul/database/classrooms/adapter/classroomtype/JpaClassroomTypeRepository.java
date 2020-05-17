package com.kul.database.classrooms.adapter.classroomtype;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaClassroomTypeRepository extends JpaRepository<ClassroomTypeEntity, Long> {
    Optional<ClassroomTypeEntity> findByName(String name);
}
