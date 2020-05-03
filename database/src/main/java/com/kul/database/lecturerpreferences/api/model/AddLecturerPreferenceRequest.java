package com.kul.database.lecturerpreferences.api.model;

import lombok.Value;

import java.time.DayOfWeek;

@Value
public class AddLecturerPreferenceRequest {
    Long userId;
    String startTime;
    String endTime;
    DayOfWeek day;
}
