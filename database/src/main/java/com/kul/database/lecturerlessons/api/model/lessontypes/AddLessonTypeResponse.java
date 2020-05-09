package com.kul.database.lecturerlessons.api.model.lessontypes;

import com.kul.database.lecturerlessons.domain.lessontype.LessonType;
import lombok.Value;

@Value
public class AddLessonTypeResponse {
    LessonType lessonType;
}
