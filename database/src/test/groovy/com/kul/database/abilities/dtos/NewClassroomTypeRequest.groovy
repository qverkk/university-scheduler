package com.kul.database.abilities.dtos

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

@Builder(builderStrategy = SimpleStrategy, prefix = "with")
class NewClassroomTypeRequest {
    String name;
}

class NewClassroomTypeRequests {
    static aNewClassroomType() {
        return new NewClassroomTypeRequest()
            .withName("Wykladowa")
    }
}
