package com.kul.api.domain.admin.management;

import lombok.Value;

import java.time.DayOfWeek;

@Value
public class LecturerPreferences {
    Long userId;
    String startTime;
    String endTime;
    DayOfWeek day;
}
