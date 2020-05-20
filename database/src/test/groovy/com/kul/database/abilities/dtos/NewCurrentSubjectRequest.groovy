package com.kul.database.abilities.dtos

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

@Builder(builderStrategy = SimpleStrategy, prefix = "with")
class NewCurrentSubjectRequest {
    String classroomTypeName;
    String classroomName;
    Long lessonId;
    String startTime;
    String endTime;
}

class NewCurrentSubjectRequests {
    static aNewCurrentSubject() {
        return new NewCurrentSubjectRequest()
            .withClassroomTypeName("Wykladowa")
            .withClassroomName("21")
            .withLessonId(1)
            .withStartTime("10:00")
            .withEndTime("11:40")
    }
}
