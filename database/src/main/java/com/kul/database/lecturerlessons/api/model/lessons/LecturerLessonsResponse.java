package com.kul.database.lecturerlessons.api.model.lessons;

import com.kul.database.lecturerlessons.api.model.areaofstudies.AreaOfStudyResponse;
import com.kul.database.lecturerlessons.api.model.lessontypes.LessonTypeResponse;
import com.kul.database.lecturerlessons.domain.Semester;
import com.kul.database.lecturerlessons.domain.StudyYear;
import com.kul.database.lecturerlessons.domain.areaofstudy.AreaOfStudy;
import com.kul.database.lecturerlessons.domain.lessontype.LessonType;
import com.kul.database.usermanagement.domain.User;
import lombok.Value;

@Value
public class LecturerLessonsResponse {
    Long id;
    Long userId;
    String lecturersName;
    String lessonName;
    AreaOfStudyResponse areaOfStudy;
    LessonTypeResponse lessonType;
    Semester semester;
    StudyYear year;
}
