package com.kul.api.adapter.admin.lessontypes;

import lombok.Value;

import java.util.List;

@Value
public class FetchAllLessonTypesResponse {
    List<FetchLessonTypeResponse> lessonTypes;
}
