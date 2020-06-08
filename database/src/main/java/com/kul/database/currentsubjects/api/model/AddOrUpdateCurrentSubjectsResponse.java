package com.kul.database.currentsubjects.api.model;

import com.kul.database.classrooms.api.model.classroom.FetchClassroomResponse;
import com.kul.database.lecturerlessons.api.model.lessons.FetchSecureLecturerLesson;
import lombok.Value;

@Value
public class AddOrUpdateCurrentSubjectsResponse {
    Long id;
    FetchClassroomResponse classroom;
    FetchSecureLecturerLesson lecturerLesson;
    String startTime;
    String endTime;
}
