package com.kul.database.lecturerlessons.api.model.lessons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateLecturerLessonRequest {
    Long userId;
    String lessonName;
    String area;
    String department;
    String lessonType;
    int semester;
    int year;
}
