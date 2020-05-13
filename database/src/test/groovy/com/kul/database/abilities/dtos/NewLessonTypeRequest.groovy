package com.kul.database.abilities.dtos

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

@Builder(builderStrategy = SimpleStrategy, prefix = "with")
class NewLessonTypeRequest {
    String type
}

class NewLessonTypeRequests {
    static aNewLessonType() {
        return new NewLessonTypeRequest()
                .withType("Cwiczenia")
    }
}
