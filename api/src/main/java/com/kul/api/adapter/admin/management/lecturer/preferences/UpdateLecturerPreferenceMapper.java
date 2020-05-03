package com.kul.api.adapter.admin.management.lecturer.preferences;

import com.kul.api.domain.admin.management.LecturerPreferences;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class UpdateLecturerPreferenceMapper {
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public static LecturerPreferences fromResponse(LecturerPreferencesResponse response) {
        return new LecturerPreferences(
                response.getUserId(),
                stringToLocalTime(response.getStartTime()),
                stringToLocalTime(response.getEndTime()),
                response.getDay()
        );
    }

    private static LocalTime stringToLocalTime(String startTime) {
        return LocalTime.parse(startTime, timeFormatter);
    }

    public static LecturerPreferencesRequest toRequest(LecturerPreferences preferences) {
        return new LecturerPreferencesRequest(
                preferences.getUserId(),
                localTimeToString(preferences.getStartTime()),
                localTimeToString(preferences.getEndTime()),
                preferences.getDay()
        );
    }

    private static String localTimeToString(LocalTime time) {
        return timeFormatter.format(time);
    }
}
