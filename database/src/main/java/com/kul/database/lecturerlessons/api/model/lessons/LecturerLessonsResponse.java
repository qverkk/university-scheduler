package com.kul.database.lecturerlessons.api.model.lessons;

import com.kul.database.lecturerlessons.domain.areaofstudy.AreaOfStudy;
import com.kul.database.lecturerlessons.domain.lessontype.LessonType;
import com.kul.database.usermanagement.domain.User;
import lombok.Value;

@Value
public class LecturerLessonsResponse {
    Long id;
    User user;
    String lessonName;
    AreaOfStudy areaOfStudy;
    LessonType lessonType;
    Integer semester;
    Integer year;
}
