package com.kul.api.adapter.admin.management.lecturer.preferences;

import lombok.Value;

@Value
public class LecturerPreferencesRequest {
    Long userId;
    String startTime;
    String endTime;
    String day;
}
