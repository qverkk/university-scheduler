package com.kul.database.classrooms.adapter.classroom;

import com.kul.database.classrooms.adapter.classroomtype.ClassroomTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface JpaClassroomRepository extends JpaRepository<ClassroomEntity, Long> {
    List<ClassroomEntity> findAllByName(String name);

    @Query("select room from classrooms room where room.name = ?2 and room.classroomTypeEntity = ?1")
    Optional<ClassroomEntity> findByClassroomTypeEntityAndName(List<ClassroomTypeEntity> entity, String name);

    Optional<ClassroomEntity> findByName(String name);

    @Transactional
    @Modifying
    @Query("delete from classrooms where name = ?2 and classroomTypeEntity = ?1")
    void deleteByClassroomTypeEntityAndName(List<ClassroomTypeEntity> classroomtype, String name);
}
