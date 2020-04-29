package com.kul.database.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateLecturerPreferenceResponse {
    private Long lecturerPreferenceId;
    private Long userId;
    private String startTime;
    private String endTime;
    private DayOfWeek day;
}
