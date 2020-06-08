package com.kul.database.currentsubjects.domain;

import com.kul.database.classrooms.domain.classroom.Classroom;
import com.kul.database.lecturerlessons.domain.LecturerLessons;
import lombok.Value;

import java.time.LocalTime;

@Value
public class UpdateCurrentSubjects {
    Long id;
    Classroom classroom;
    LecturerLessons lecturerLesson;
    LocalTime startTime;
    LocalTime endTime;
}
