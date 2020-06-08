package com.kul.database.lecturerlessons.domain.lessontype;

import lombok.Getter;

@Getter
public class LessonType {
    private final Long id;
    private final String type;

    public LessonType(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    public LessonType(String type) {
        this(null, type);
    }

    public static LessonType newLessonType(String type) {
        return new LessonType(type);
    }
}
