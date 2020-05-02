package com.kul.database.lecturerpreferences.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateLecturerPreferenceResponse {
    private Long lecturerPreferenceId;
    private Long userId;
    private String startTime;
    private String endTime;
    private String day;
}
