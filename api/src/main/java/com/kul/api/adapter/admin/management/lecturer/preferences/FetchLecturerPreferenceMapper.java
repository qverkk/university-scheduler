package com.kul.api.adapter.admin.management.lecturer.preferences;

import com.kul.api.domain.admin.management.LecturerPreferences;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FetchLecturerPreferenceMapper {
    public static LecturerPreferences fromResponse(Long userId, FetchLecturerPreferenceResponse response, DayOfWeek day) {
        return new LecturerPreferences(
                userId,
                stringToLocalTime(response.getStartTime()),
                stringToLocalTime(response.getEndTime()),
                day
        );
    }

    private static LocalTime stringToLocalTime(String startTime) {
        List<Integer> numbers = Arrays.stream(startTime.split(":"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        return LocalTime.of(numbers.get(0), numbers.get(1));
    }
}
