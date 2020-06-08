package com.kul.database.currentsubjects.adapter;

import com.kul.database.classrooms.adapter.classroom.ClassroomEntity;
import com.kul.database.lecturerlessons.adapter.lesson.LecturerLessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaCurrentSubjectsRepository extends JpaRepository<CurrentSubjectsEntity, Long> {

    Optional<CurrentSubjectsEntity> findByClassroomEntityAndLecturerLessonEntity(
            ClassroomEntity classroom,
            LecturerLessonEntity lesson
    );
}
