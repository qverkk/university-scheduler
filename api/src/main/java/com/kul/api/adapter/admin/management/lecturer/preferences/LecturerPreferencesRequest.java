package com.kul.api.adapter.admin.management.lecturer.preferences;

import lombok.Value;

import java.time.DayOfWeek;

@Value
public class LecturerPreferencesRequest {
    Long userId;
    String startTime;
    String endTime;
    DayOfWeek day;
}
