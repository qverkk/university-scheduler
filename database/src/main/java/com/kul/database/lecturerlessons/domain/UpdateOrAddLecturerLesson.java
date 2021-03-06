package com.kul.database.lecturerlessons.domain;

import com.kul.database.lecturerlessons.domain.areaofstudy.AreaOfStudy;
import com.kul.database.lecturerlessons.domain.lessontype.LessonType;
import lombok.Value;

@Value
public class UpdateOrAddLecturerLesson {
    Long userId;
    String lessonName;
    AreaOfStudy areaOfStudy;
    Semester semester;
    StudyYear year;
    LessonType lessonType;
}
