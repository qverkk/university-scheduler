package com.kul.database.lecturerlessons.api.model.lessons;

import com.kul.database.lecturerlessons.domain.areaofstudy.AreaOfStudy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateLecturerLessonRequest {
    Long userId;
    String lessonName;
    AreaOfStudy areaOfStudy;
    int semester;
    int year;
}
