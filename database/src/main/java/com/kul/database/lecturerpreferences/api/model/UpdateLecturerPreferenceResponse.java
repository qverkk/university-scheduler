package com.kul.database.lecturerpreferences.api.model;

import lombok.Value;

@Value
public class UpdateLecturerPreferenceResponse {
    Long lecturerPreferenceId;
    Long userId;
    String startTime;
    String endTime;
    String day;
}
