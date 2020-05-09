package com.kul.database.abilities.dtos

import com.kul.database.lecturerlessons.domain.areaofstudy.AreaOfStudy
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

@Builder(builderStrategy = SimpleStrategy, prefix = "with")
class NewLecturerLessonRequest {
    Long userId;
    String lessonName;
    AreaOfStudy areaOfStudy;
    int semester;
    int year;
}

class NewLecturerLessonsRequests {
    static aNewLecturerLesson() {
        return new NewLecturerLessonRequest()
            .withAreaOfStudy(AreaOfStudy.INFORMATYKA)
            .withLessonName("Lekcja")
            .withSemester(2)
            .withYear(4)
            .withUserId(0)
    }
}
