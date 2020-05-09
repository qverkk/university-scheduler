package com.kul.database.lecturerlessons.adapter.lessontype;

import com.kul.database.lecturerlessons.domain.lessontype.LessonType;

public class LessonTypeEntityMapper {

    public static LessonTypeEntity fromDomain(LessonType lessonType) {
        return new LessonTypeEntity(
                lessonType.getId(),
                lessonType.getType()
        );
    }

    public static LessonType toDomain(LessonTypeEntity lessonTypeEntity) {
        return new LessonType(
                lessonTypeEntity.getId(),
                lessonTypeEntity.getType()
        );
    }
}
