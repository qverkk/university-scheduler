package com.kul.database.abilities.dtos

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

@Builder(builderStrategy = SimpleStrategy, prefix = "with")
class NewClassroomRequest {
    String name
    List<String> classroomTypes
    Integer classroomSize
}

class NewClassroomRequests {
    static aNewClassroom() {
        return new NewClassroomRequest()
                .withName("B21")
                .withClassroomTypes(
                        new LinkedList<String>().add("Wykladowa") as List<String>
                )
    }
}
