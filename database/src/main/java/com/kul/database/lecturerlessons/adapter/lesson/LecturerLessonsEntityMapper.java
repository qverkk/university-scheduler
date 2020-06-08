package com.kul.database.lecturerlessons.adapter.lesson;

import com.kul.database.lecturerlessons.adapter.areaofstudy.AreaOfStudyEntityMapper;
import com.kul.database.lecturerlessons.adapter.lessontype.LessonTypeEntityMapper;
import com.kul.database.lecturerlessons.domain.LecturerLessons;

public class LecturerLessonsEntityMapper {
    public static LecturerLessons toDomain(LecturerLessonEntity lecturerLessonEntity) {
        return new LecturerLessons(
                lecturerLessonEntity.getId(),
                lecturerLessonEntity.getUser(),
                lecturerLessonEntity.getLessonName(),
                AreaOfStudyEntityMapper.toDomain(lecturerLessonEntity.getAreaOfStudy()),
                LessonTypeEntityMapper.toDomain(lecturerLessonEntity.getLessonType()),
                lecturerLessonEntity.getSemester(),
                lecturerLessonEntity.getYear(),
                lecturerLessonEntity.getVersion()
        );
    }

    public static LecturerLessonEntity fromDomain(LecturerLessons lecturerLessons) {
        return new LecturerLessonEntity(
                lecturerLessons.getId(),
                lecturerLessons.getUser(),
                lecturerLessons.getLessonName(),
                AreaOfStudyEntityMapper.fromDomain(lecturerLessons.getAreaOfStudy()),
                LessonTypeEntityMapper.fromDomain(lecturerLessons.getLessonType()),
                lecturerLessons.getSemester(),
                lecturerLessons.getYear(),
                lecturerLessons.getVersion()
        );
    }
}
