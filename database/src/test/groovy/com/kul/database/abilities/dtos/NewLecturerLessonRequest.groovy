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
    String semester;
    String year;
}


class NewLecturerLessonsRequests {
    static aNewLecturerLesson() {
        return new NewLecturerLessonRequest()
                .withLessonName("Lekcja")
                .withSemester("SUMMER")
                .withYear("FOURTH")
                .withUserId(0)
                .withDepartment("Jakis tam wydzial")
                .withArea("Informatyka")
                .withLessonType("Cwiczenia")
    }
}
