package com.kul.database.abilities.dtos


import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

@Builder(builderStrategy = SimpleStrategy, prefix = "with")
class NewLecturerLessonRequest {
    Long userId;
    String lessonName;
    String area;
    String department;
    String lessonType;
    int semester;
    int year;
}


class NewLecturerLessonsRequests {
    static aNewLecturerLesson() {
        return new NewLecturerLessonRequest()
                .withLessonName("Lekcja")
                .withSemester(2)
                .withYear(4)
                .withUserId(0)
                .withDepartment("Jakis tam wydzial")
                .withArea("Informatyka")
                .withLessonType("Cwiczenia")
    }
}
