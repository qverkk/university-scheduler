package com.kul.api.adapter.admin.lessons;

import lombok.Value;

import java.util.List;

@Value
public class FetchAllLessonsResponse {
    List<FetchLessonResponse> lecturerLessons;
}
