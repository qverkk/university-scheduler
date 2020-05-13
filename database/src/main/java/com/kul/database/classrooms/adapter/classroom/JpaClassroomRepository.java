package com.kul.database.classrooms.adapter.classroom;

import com.kul.database.classrooms.adapter.classroomtype.ClassroomTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaClassroomRepository extends JpaRepository<ClassroomEntity, Long> {
    List<ClassroomEntity> findAllByName(String name);

    void deleteByClassroomTypeEntityAndName(ClassroomTypeEntity classroomtype, String name);
}
