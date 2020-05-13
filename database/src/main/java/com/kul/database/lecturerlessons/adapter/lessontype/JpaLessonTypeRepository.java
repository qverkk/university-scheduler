package com.kul.database.lecturerlessons.adapter.lessontype;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaLessonTypeRepository extends JpaRepository<LessonTypeEntity, Long> {
    Optional<LessonTypeEntity> findByType(String type);
}
