package com.kul.database.currentsubjects.api.model;

import com.kul.database.classrooms.api.model.classroom.FetchClassroomResponse;
import com.kul.database.classrooms.api.model.classroomtype.FetchClassroomTypesResponse;
import com.kul.database.classrooms.domain.classroom.Classroom;
import com.kul.database.classrooms.domain.classroomtype.ClassroomType;
import com.kul.database.currentsubjects.domain.CurrentSubjects;
import com.kul.database.currentsubjects.domain.UpdateCurrentSubjects;
import com.kul.database.lecturerlessons.api.model.areaofstudies.AreaOfStudyResponse;
import com.kul.database.lecturerlessons.api.model.lessons.FetchSecureLecturerLesson;
import com.kul.database.lecturerlessons.api.model.lessontypes.LessonTypeResponse;
import com.kul.database.lecturerlessons.domain.LecturerLessons;
import com.kul.database.usermanagement.domain.User;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class AddOrUpdateCurrentSubjectsMapper {
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public static UpdateCurrentSubjects toDomain(AddOrUpdateCurrentSubjectsRequest request) {
        final Classroom classroom = Classroom.newForName(request.getClassroomName());
        classroom.getClassroomTypes().add(ClassroomType.newForName(request.getClassroomTypeName()));
        return new UpdateCurrentSubjects(
                null,
                classroom,
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
                classroom.getClassroomTypes().stream()
                        .map(c -> new FetchClassroomTypesResponse(
                                c.getId(),
                                c.getName()
                        )).collect(Collectors.toList()),
                classroom.getClassroomSize()
        );
    }


    private static LocalTime stringToLocalTime(String startTime) {
        return LocalTime.parse(startTime, timeFormatter);
    }

    private static String localTimeToString(LocalTime time) {
        return timeFormatter.format(time);
    }
}
