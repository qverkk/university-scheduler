package com.kul.database.lecturerlessons.domain.lessontype;

import java.util.List;
import java.util.Optional;

public interface LessonTypeRepository {
    Optional<LessonType> findByLessonTypeName(String lessonType);

    List<LessonType> findAllLessonTypes();

    LessonType save(LessonType lessonType);
}
