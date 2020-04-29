package com.kul.api.adapter.admin.management.lecturer.preferences;

import lombok.Value;

import java.time.DayOfWeek;

@Value
public class LecturerPreferencesResponse {
    Long lecturerPreferenceId;
    Long userId;
    String startTime;
    String endTime;
    DayOfWeek day;
}
