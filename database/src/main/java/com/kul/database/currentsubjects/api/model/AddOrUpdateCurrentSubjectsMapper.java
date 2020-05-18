package com.kul.database.currentsubjects.api.model;

import com.kul.database.classrooms.api.model.classroom.FetchClassroomResponse;
import com.kul.database.classrooms.api.model.classroomtype.FetchClassroomTypesResponse;
import com.kul.database.classrooms.domain.classroom.Classroom;
import com.kul.database.currentsubjects.domain.CurrentSubjects;
import com.kul.database.currentsubjects.domain.UpdateCurrentSubjects;
import com.kul.database.lecturerlessons.api.model.lessons.FetchSecureLecturerLesson;
import com.kul.database.lecturerlessons.api.model.areaofstudies.AreaOfStudyResponse;
import com.kul.database.lecturerlessons.api.model.lessontypes.LessonTypeResponse;
import com.kul.database.lecturerlessons.domain.LecturerLessons;
import com.kul.database.usermanagement.domain.User;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AddOrUpdateCurrentSubjectsMapper {
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public static UpdateCurrentSubjects toDomain(AddOrUpdateCurrentSubjectsRequest request) {
        return new UpdateCurrentSubjects(
                null,
                Classroom.newForNameAndTypeName(request.getClassroomName(), request.getClassroomTypeName()),
                LecturerLessons.existingForId(request.getLessonId()),
                stringToLocalTime(request.getStartTime()),
                stringToLocalTime(request.getEndTime())
        );
    }

    public static AddOrUpdateCurrentSubjectsResponse fromDomain(CurrentSubjects currentSubjects) {
        return new AddOrUpdateCurrentSubjectsResponse(
                currentSubjects.getId(),
                classroomMapToFetchClassroom(currentSubjects.getClassroom()),
                lecturerLessonMapToFetchLecturerLesson(currentSubjects.getLecturerLesson()),
                localTimeToString(currentSubjects.getStartTime()),
                localTimeToString(currentSubjects.getEndTime())
        );
    }

    private static FetchSecureLecturerLesson lecturerLessonMapToFetchLecturerLesson(LecturerLessons lecturerLesson) {
        return new FetchSecureLecturerLesson(
                lecturerLesson.getId(),
                getLecturerFullName(lecturerLesson.getUser()),
                lecturerLesson.getLessonName(),
                new AreaOfStudyResponse(
                        lecturerLesson.getAreaOfStudy().getId(),
                        lecturerLesson.getAreaOfStudy().getArea(),
                        lecturerLesson.getAreaOfStudy().getDepartment()
                ),
                new LessonTypeResponse(
                        lecturerLesson.getLessonType().getId(),
                        lecturerLesson.getLessonType().getType()
                ),
                lecturerLesson.getSemester(),
                lecturerLesson.getYear(),
                lecturerLesson.getVersion()
        );
    }

    private static String getLecturerFullName(User user) {
        return user.getFirstName() + " " + user.getLastName();
    }

    private static FetchClassroomResponse classroomMapToFetchClassroom(Classroom classroom) {
        return new FetchClassroomResponse(
                classroom.getId(),
                classroom.getName(),
                new FetchClassroomTypesResponse(
                        classroom.getClassroomType().getId(),
                        classroom.getClassroomType().getName()
                )
        );
    }


    private static LocalTime stringToLocalTime(String startTime) {
        return LocalTime.parse(startTime, timeFormatter);
    }

    private static String localTimeToString(LocalTime time) {
        return timeFormatter.format(time);
    }
}
