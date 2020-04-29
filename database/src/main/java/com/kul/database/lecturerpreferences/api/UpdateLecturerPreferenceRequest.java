package com.kul.database.lecturerpreferences.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateLecturerPreferenceRequest {
    private Long userId;
    private String startTime;
    private String endTime;
    private DayOfWeek day;
}
