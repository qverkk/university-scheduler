package com.kul.database.lecturerpreferences.api;

import lombok.Value;

import java.time.LocalTime;

@Value
public class FetchLecturerPreferenceResponse {
    LocalTime startTime;
    LocalTime endTime;
}
