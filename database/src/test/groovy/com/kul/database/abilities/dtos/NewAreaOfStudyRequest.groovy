package com.kul.database.abilities.dtos

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

@Builder(builderStrategy = SimpleStrategy, prefix = "with")
class NewAreaOfStudyRequest {
    String area;
    String department;
}

class NewAreaOfStudyRequests {
    static aNewAreaOfStudy() {
        return new NewAreaOfStudyRequest()
                .withArea("Informatyka")
                .withDepartment("Jakis tam wydzial")
    }
}

