package com.kul.api.adapter.admin.lessons;

import com.kul.api.adapter.admin.areaofstudies.FetchAreaOfStudiesResponse;
import com.kul.api.adapter.admin.lessontypes.FetchLessonTypeResponse;
import com.kul.api.domain.admin.lessons.Semester;
import com.kul.api.domain.admin.lessons.StudyYear;
import lombok.Value;

@Value
public class UpdateLecturerLessonResponse {
    Long id;
    Long userId;
    String lecturersName;
    String lessonName;
    FetchAreaOfStudiesResponse areaOfStudy;
    Semester semester;
    StudyYear year;
    FetchLessonTypeResponse lessonType;
}
