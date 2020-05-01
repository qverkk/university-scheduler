package com.kul.api.adapter.admin.management.lecturer.preferences;

import lombok.Value;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Value
public class LecturerPreferencesResponse {
    Long lecturerPreferenceId;
    Long userId;
    LocalTime startTime;
    LocalTime endTime;
    DayOfWeek day;
}
