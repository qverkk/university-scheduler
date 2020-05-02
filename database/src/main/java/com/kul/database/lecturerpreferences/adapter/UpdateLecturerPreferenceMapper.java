package com.kul.database.lecturerpreferences.adapter;

import com.kul.database.lecturerpreferences.api.UpdateLecturerPreferenceResponse;
import com.kul.database.lecturerpreferences.domain.LecturerPreferences;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class UpdateLecturerPreferenceMapper {
    public static UpdateLecturerPreferenceResponse fromDomain(LecturerPreferences lecturerPreferences) {
        return new UpdateLecturerPreferenceResponse(
                lecturerPreferences.getId(),
                lecturerPreferences.getUser().getId(),
                localTimeToString(lecturerPreferences.getStartTime()),
                localTimeToString(lecturerPreferences.getEndTime()),
                lecturerPreferences.getDay().name()
        );
    }

    private static String localTimeToString(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return formatter.format(time);
    }
}
