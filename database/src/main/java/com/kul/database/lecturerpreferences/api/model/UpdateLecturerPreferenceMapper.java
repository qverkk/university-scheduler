package com.kul.database.lecturerpreferences.api.model;

import com.kul.database.lecturerpreferences.domain.LecturerPreferences;
import com.kul.database.lecturerpreferences.domain.UpdateLecturerPreference;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class UpdateLecturerPreferenceMapper {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    public static UpdateLecturerPreferenceResponse fromDomain(LecturerPreferences lecturerPreferences) {
        return new UpdateLecturerPreferenceResponse(
                lecturerPreferences.getId(),
                lecturerPreferences.getUser().getId(),
                localTimeToString(lecturerPreferences.getStartTime()),
                localTimeToString(lecturerPreferences.getEndTime()),
                lecturerPreferences.getDay().name()
        );
    }

    public static UpdateLecturerPreference toDomain(UpdateLecturerPreferenceRequest request) {
        return new UpdateLecturerPreference(
                request.getUserId(),
                LocalTime.parse(request.getStartTime(), formatter),
                LocalTime.parse(request.getEndTime(), formatter),
                DayOfWeek.valueOf(request.getDay())
        );
    }

    private static String localTimeToString(LocalTime time) {
        return formatter.format(time);
    }
}