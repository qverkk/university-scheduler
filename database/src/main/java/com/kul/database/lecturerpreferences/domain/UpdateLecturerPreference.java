package com.kul.database.lecturerpreferences.domain;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Value
@AllArgsConstructor
public class UpdateLecturerPreference {
    Long userId;
    LocalTime startTime;
    LocalTime endTime;
    DayOfWeek day;
}
