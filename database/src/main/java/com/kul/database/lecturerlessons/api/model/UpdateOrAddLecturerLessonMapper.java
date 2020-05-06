package com.kul.database.lecturerlessons.api.model;

import com.kul.database.lecturerlessons.domain.LecturerLessons;
import com.kul.database.lecturerlessons.domain.UpdateOrAddLecturerLesson;

public class UpdateOrAddLecturerLessonMapper {
    public static UpdateOrAddLecturerLesson toDomain(UpdateLecturerLessonRequest request) {
        return new UpdateOrAddLecturerLesson(
                request.getUserId(),
                request.getLessonName(),
                request.getAreaOfStudy(),
                request.getSemester(),
                request.getYear()
        );
    }

    public static UpdateLecturerLessonResponse fromDomain(LecturerLessons lecturerLessons) {
        return new UpdateLecturerLessonResponse(
                lecturerLessons.getId(),
                lecturerLessons.getUser().getId(),
                lecturerLessons.getLessonName(),
                lecturerLessons.getAreaOfStudy(),
                lecturerLessons.getSemester(),
                lecturerLessons.getYear()
        );
    }
}
