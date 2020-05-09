package com.kul.database.lecturerlessons.domain;

import com.kul.database.lecturerlessons.domain.areaofstudy.AreaOfStudy;
import lombok.Value;

@Value
public class UpdateOrAddLecturerLesson {
    Long userId;
    String lessonName;
    AreaOfStudy areaOfStudy;
    int semester;
    int year;
    String lessonType;
}
