package com.kul.api.adapter.admin.management.lecturer.preferences;

import com.kul.api.domain.admin.management.LecturerPreferences;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateLecturerPreferenceMapper {
    public static LecturerPreferences fromResponse(LecturerPreferencesResponse response) {
        return new LecturerPreferences(
                response.getUserId(),
                stringToLocalTime(response.getStartTime()),
                stringToLocalTime(response.getEndTime()),
                response.getDay()
        );
    }

    private static LocalTime stringToLocalTime(String startTime) {
        List<Integer> numbers = Arrays.stream(startTime.split(":"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        return LocalTime.of(numbers.get(0), numbers.get(1));
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return formatter.format(time);
    }
}
