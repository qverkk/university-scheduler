package com.kul.database.lecturerpreferences.api.model;

import lombok.Value;

import java.time.DayOfWeek;

@Value
public class AddLecturePreferenceResponse {
    Long lecturerPreferenceId;
    Long userId;
    String startTime;
    String endTime;
    DayOfWeek day;
}
