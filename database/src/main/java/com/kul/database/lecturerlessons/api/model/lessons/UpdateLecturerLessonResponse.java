package com.kul.database.lecturerlessons.api.model.lessons;

import com.kul.database.lecturerlessons.domain.AreaOfStudy;
import lombok.Value;

@Value
public class UpdateLecturerLessonResponse {
    Long id;
    Long userId;
    String lessonName;
    AreaOfStudy areaOfStudy;
    int semester;
    int year;
}
