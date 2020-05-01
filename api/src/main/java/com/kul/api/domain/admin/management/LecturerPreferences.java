package com.kul.api.domain.admin.management;

import lombok.Value;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Value
public class LecturerPreferences {
    Long userId;
    LocalTime startTime;
    LocalTime endTime;
    DayOfWeek day;
}
