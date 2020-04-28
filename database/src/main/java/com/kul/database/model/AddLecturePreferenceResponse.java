package com.kul.database.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddLecturePreferenceResponse {
    private Long lecturerPreferenceId;
    private Long userId;
    private String startTime;
    private String endTime;
    private DayOfWeek day;
}
