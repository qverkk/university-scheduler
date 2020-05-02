package com.kul.database.lecturerpreferences.adapter;

import com.kul.database.lecturerpreferences.api.FetchLecturerPreferenceResponse;
import com.kul.database.lecturerpreferences.domain.LecturerPreferences;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FetchLecturerPreferenceMapper {
    public static FetchLecturerPreferenceResponse toResponse(LecturerPreferences lecturerPreferences) {
        return new FetchLecturerPreferenceResponse(
                localTimeToString(lecturerPreferences.getStartTime()),
                localTimeToString(lecturerPreferences.getEndTime())
        );
    }

    private static String localTimeToString(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return formatter.format(time);
    }
}
