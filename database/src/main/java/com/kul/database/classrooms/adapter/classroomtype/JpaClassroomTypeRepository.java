package com.kul.database.classrooms.adapter.classroomtype;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JpaClassroomTypeRepository extends JpaRepository<ClassroomTypeEntity, Long> {
    Optional<ClassroomTypeEntity> findByName(String name);

    @Query("select type from classroom_types type where type.name in ?1")
    List<ClassroomTypeEntity> findAllByNames(List<String> names);
}
