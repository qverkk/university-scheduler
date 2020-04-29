package com.kul.database.lecturerpreferences.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddLecturerPreference {
    private Long userId;
    private String startTime;
    private String endTime;
    private DayOfWeek day;
}
