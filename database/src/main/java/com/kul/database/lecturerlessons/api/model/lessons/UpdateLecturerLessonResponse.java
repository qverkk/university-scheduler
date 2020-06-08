package com.kul.database.lecturerlessons.api.model.lessons;

import com.kul.database.lecturerlessons.api.model.areaofstudies.AreaOfStudyResponse;
import com.kul.database.lecturerlessons.domain.Semester;
import com.kul.database.lecturerlessons.domain.StudyYear;
import com.kul.database.lecturerlessons.domain.lessontype.LessonType;
import lombok.Value;

@Value
public class UpdateLecturerLessonResponse {
    Long id;
    Long userId;
    String lessonName;
    AreaOfStudyResponse areaOfStudy;
    Semester semester;
    StudyYear year;
    LessonType lessonType;
}
