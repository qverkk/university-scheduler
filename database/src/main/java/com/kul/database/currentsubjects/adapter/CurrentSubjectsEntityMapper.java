package com.kul.database.currentsubjects.adapter;

import com.kul.database.classrooms.adapter.classroom.ClassroomEntityMapper;
import com.kul.database.currentsubjects.domain.CurrentSubjects;
import com.kul.database.lecturerlessons.adapter.lesson.LecturerLessonsEntityMapper;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CurrentSubjectsEntityMapper {
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public static CurrentSubjects toDomain(CurrentSubjectsEntity entity) {
        return new CurrentSubjects(
                entity.getId(),
                ClassroomEntityMapper.toDomain(entity.getClassroomEntity()),
                LecturerLessonsEntityMapper.toDomain(entity.getLecturerLessonEntity()),
                entity.getStartTime(),
                entity.getEndTime()
        );
    }

    public static CurrentSubjectsEntity fromDomain(CurrentSubjects entity) {
        return new CurrentSubjectsEntity(
                entity.getId(),
                ClassroomEntityMapper.fromDomain(entity.getClassroom()),
                LecturerLessonsEntityMapper.fromDomain(entity.getLecturerLesson()),
                entity.getStartTime(),
                entity.getEndTime()
        );
    }

    private static LocalTime stringToLocalTime(String startTime) {
        return LocalTime.parse(startTime, timeFormatter);
    }

    private static String localTimeToString(LocalTime time) {
        return timeFormatter.format(time);
    }
}
