package com.kul.database.lecturerpreferences.adapter;

import com.kul.database.lecturerpreferences.api.UpdateLecturerPreferenceResponse;
import com.kul.database.lecturerpreferences.domain.LecturerPreferences;

import java.time.LocalTime;

public class UpdateLecturerPreferenceMapper {
    public static UpdateLecturerPreferenceResponse fromDomain(LecturerPreferences lecturerPreferences) {
        return new UpdateLecturerPreferenceResponse(
                lecturerPreferences.getId(),
                lecturerPreferences.getUser().getId(),
                localTimeToHourAndMinute(lecturerPreferences.getStartTime()),
                localTimeToHourAndMinute(lecturerPreferences.getEndTime()),
                lecturerPreferences.getDay().name()
        );
    }

    private static String localTimeToHourAndMinute(LocalTime time) {
        return time.getHour() + ":" + time.getMinute();
    }
}
