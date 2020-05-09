package com.kul.database.lecturerlessons.api.model.lessontypes;

import com.kul.database.lecturerlessons.domain.lessontype.LessonType;
import lombok.Value;

import java.util.List;

@Value
public class FetchAllLessonTypesResponse {
    List<LessonType> lessonTypes;
}
