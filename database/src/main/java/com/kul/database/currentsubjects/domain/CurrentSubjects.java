package com.kul.database.currentsubjects.domain;

import com.kul.database.classrooms.domain.classroom.Classroom;
import com.kul.database.lecturerlessons.domain.LecturerLessons;
import com.kul.database.lecturerpreferences.domain.exceptions.LecturerPreferenceInvalidTime;
import lombok.Getter;
import lombok.Value;

import java.time.LocalTime;

@Getter
public class CurrentSubjects {
    private final Long id;
    private final Classroom classroom;
    private final LecturerLessons lecturerLesson;
    private LocalTime startTime;
    private LocalTime endTime;

    public CurrentSubjects(Long id, Classroom classroom, LecturerLessons lecturerLesson, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.classroom = classroom;
        this.lecturerLesson = lecturerLesson;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static CurrentSubjects newForClassroomAndLesson(Classroom classroom, LecturerLessons lesson) {
        return new CurrentSubjects(
                null,
                classroom,
                lesson,
                null,
                null
        );
    }

    public CurrentSubjects updateTimes(LocalTime startTime, LocalTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new LecturerPreferenceInvalidTime("Start time must be before end time");
        }
        return new CurrentSubjects(
                id,
                classroom,
                lecturerLesson,
                startTime,
                endTime
        );
    }
}
