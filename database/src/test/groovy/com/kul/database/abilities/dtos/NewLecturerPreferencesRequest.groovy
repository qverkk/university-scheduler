package com.kul.database.abilities.dtos

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

import java.time.DayOfWeek

@Builder(builderStrategy = SimpleStrategy, prefix = "with")
class NewLecturerPreferencesRequest {
    Long userId;
    String startTime;
    String endTime;
    DayOfWeek day;
}

class NewLecturerPreferencesRequests {
    static aNewLecturerPreference() {
        return new NewLecturerPreferencesRequest()
            .withUserId(2)
            .withStartTime("08:00")
            .withEndTime("15:00")
            .withDay(DayOfWeek.MONDAY)
    }

    static aExistingLecturerPreference() {
        return aNewLecturerPreference()
    }
}
