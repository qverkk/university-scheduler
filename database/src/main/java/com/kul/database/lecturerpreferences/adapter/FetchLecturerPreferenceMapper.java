package com.kul.database.lecturerpreferences.adapter;

import com.kul.database.lecturerpreferences.api.FetchLecturerPreferenceResponse;
import com.kul.database.lecturerpreferences.domain.LecturerPreferences;

import java.time.LocalTime;

public class FetchLecturerPreferenceMapper {
    public static FetchLecturerPreferenceResponse toResponse(LecturerPreferences lecturerPreferences) {
        return new FetchLecturerPreferenceResponse(
                localTimeToHourAndMinute(lecturerPreferences.getStartTime()),
                localTimeToHourAndMinute(lecturerPreferences.getEndTime())
        );
    }

    private static String localTimeToHourAndMinute(LocalTime time) {
        return time.getHour() + ":" + time.getMinute();
    }
}
