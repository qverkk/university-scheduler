package com.kul.api.adapter.admin.management.lecturer.preferences;

import com.kul.api.domain.admin.management.LecturerPreferences;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FetchLecturerPreferenceMapper {
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public static LecturerPreferences fromResponse(Long userId, FetchLecturerPreferenceResponse response, DayOfWeek day) {
        return new LecturerPreferences(
                userId,
                stringToLocalTime(response.getStartTime()),
                stringToLocalTime(response.getEndTime()),
                day
        );
    }

    private static LocalTime stringToLocalTime(String startTime) {
        return LocalTime.parse(startTime, timeFormatter);
    }
}
