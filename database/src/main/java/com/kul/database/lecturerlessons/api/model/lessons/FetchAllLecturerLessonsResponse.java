package com.kul.database.lecturerlessons.api.model.lessons;

import lombok.Value;

import java.util.List;

@Value
public class FetchAllLecturerLessonsResponse {
    List<LecturerLessonsResponse> lecturerLessons;
}
