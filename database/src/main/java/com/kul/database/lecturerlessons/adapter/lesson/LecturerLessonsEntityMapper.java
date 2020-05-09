package com.kul.database.lecturerlessons.adapter.lesson;

import com.kul.database.lecturerlessons.adapter.lessontype.LessonTypeEntityMapper;
import com.kul.database.lecturerlessons.domain.LecturerLessons;

public class LecturerLessonsEntityMapper {
    public static LecturerLessons toDomain(LecturerLessonEntity lecturerLessonEntity) {
        return new LecturerLessons(
                lecturerLessonEntity.getId(),
                lecturerLessonEntity.getUser(),
                lecturerLessonEntity.getLessonName(),
                lecturerLessonEntity.getAreaOfStudy(),
                LessonTypeEntityMapper.toDomain(lecturerLessonEntity.getLessonType()),
                lecturerLessonEntity.getSemester(),
                lecturerLessonEntity.getYear(),
                lecturerLessonEntity.getVersion()
        );
    }
}
