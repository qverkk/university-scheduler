package com.kul.database.lecturerlessons.api.model.lessontypes;

import lombok.Value;

import java.util.List;

@Value
public class FetchAllLessonTypesResponse {
    List<LessonTypeResponse> lessonTypes;
}
