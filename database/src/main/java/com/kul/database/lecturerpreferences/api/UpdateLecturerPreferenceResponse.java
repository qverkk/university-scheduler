package com.kul.database.lecturerpreferences.api;

import com.kul.database.lecturerpreferences.domain.LecturerPreferences;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateLecturerPreferenceResponse {
    private Long lecturerPreferenceId;
    private Long userId;
    private LocalTime startTime;
    private LocalTime endTime;
    private DayOfWeek day;

    public static UpdateLecturerPreferenceResponse fromDomain(LecturerPreferences lecturerPreferences) {
        return new UpdateLecturerPreferenceResponse(
                lecturerPreferences.getId(),
                lecturerPreferences.getUser().getId(),
                lecturerPreferences.getStartTime(),
                lecturerPreferences.getEndTime(),
                lecturerPreferences.getDay()
        );
    }
}
