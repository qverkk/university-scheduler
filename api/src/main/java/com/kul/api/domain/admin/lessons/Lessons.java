package com.kul.api.domain.admin.lessons;

import com.kul.api.adapter.admin.areaofstudies.FetchAreaOfStudiesResponse;
import com.kul.api.adapter.admin.lessontypes.FetchLessonTypeResponse;
import lombok.Value;

@Value
public class Lessons {
    Long id;
    Long userId;
    String lecturersName;
    String lessonName;
    FetchAreaOfStudiesResponse areaOfStudy;
    FetchLessonTypeResponse lessonType;
    Semester semester;
    StudyYear year;
}
