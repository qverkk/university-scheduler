package com.kul.database.lecturerlessons.api.model;

import com.kul.database.lecturerlessons.domain.AreaOfStudy;
import lombok.Value;

@Value
public class UpdateLecturerLessonRequest {
    Long userId;
    String lessonName;
    AreaOfStudy areaOfStudy;
    int semester;
    int year;
}
