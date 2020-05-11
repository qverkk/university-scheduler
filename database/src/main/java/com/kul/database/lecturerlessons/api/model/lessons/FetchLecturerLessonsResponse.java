package com.kul.database.lecturerlessons.api.model.lessons;

import lombok.Value;

import java.util.List;

@Value
public class FetchLecturerLessonsResponse {
    List<LecturerLessonsResponse> lecturerLessons;
}
