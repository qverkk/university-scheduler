package com.kul.database.lecturerlessons.api.model.lessons;

import com.kul.database.infrastructure.constraints.AnyOfEnumValues;
import com.kul.database.lecturerlessons.domain.Semester;
import com.kul.database.lecturerlessons.domain.StudyYear;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Year;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateLecturerLessonRequest {
    Long userId;
    String lessonName;
    String area;
    String department;
    String lessonType;
    @AnyOfEnumValues(Semester.class)
    Semester semester;
    @AnyOfEnumValues(StudyYear.class)
    StudyYear year;
}
