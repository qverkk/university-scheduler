package com.kul.database.lecturerlessons.api.model.lessons;

import com.kul.database.lecturerlessons.api.model.areaofstudies.AreaOfStudyResponse;
import com.kul.database.lecturerlessons.api.model.lessontypes.LessonTypeResponse;
import com.kul.database.lecturerlessons.domain.Semester;
import com.kul.database.lecturerlessons.domain.StudyYear;
import lombok.Value;

@Value
public class FetchSecureLecturerLesson {
    Long id;
    String lecturerFullName;
    String lessonName;
    AreaOfStudyResponse areaOfStudy;
    LessonTypeResponse lessonType;
    Semester semester;
    StudyYear studyYear;
    Long version;
}
