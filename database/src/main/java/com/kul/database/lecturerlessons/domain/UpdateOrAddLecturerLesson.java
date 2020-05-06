package com.kul.database.lecturerlessons.domain;

import lombok.Value;

@Value
public class UpdateOrAddLecturerLesson {
    Long userId;
    String lessonName;
    AreaOfStudy areaOfStudy;
    int semester;
    int year;
}
