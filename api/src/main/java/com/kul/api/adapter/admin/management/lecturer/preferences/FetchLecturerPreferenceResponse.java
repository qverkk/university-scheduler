package com.kul.api.adapter.admin.management.lecturer.preferences;

import lombok.Value;

import java.time.LocalTime;

@Value
public class FetchLecturerPreferenceResponse {
    LocalTime startTime;
    LocalTime endTime;
}
