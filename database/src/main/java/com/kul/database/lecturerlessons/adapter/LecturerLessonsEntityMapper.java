package com.kul.database.lecturerlessons.adapter;

import com.kul.database.lecturerlessons.domain.LecturerLessons;

public class LecturerLessonsEntityMapper {
    public static LecturerLessons toDomain(LecturerLessonEntity lecturerLessonEntity) {
        return new LecturerLessons(
                lecturerLessonEntity.getId(),
                lecturerLessonEntity.getUser(),
                lecturerLessonEntity.getLessonName(),
                lecturerLessonEntity.getAreaOfStudy(),
                lecturerLessonEntity.getSemester(),
                lecturerLessonEntity.getYear(),
                lecturerLessonEntity.getVersion()
        );
    }
}
