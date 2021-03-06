package com.kul.database.lecturerlessons.api.model.lessons;

import com.kul.database.lecturerlessons.api.model.areaofstudies.AreaOfStudyResponse;
import com.kul.database.lecturerlessons.domain.LecturerLessons;
import com.kul.database.lecturerlessons.domain.Semester;
import com.kul.database.lecturerlessons.domain.StudyYear;
import com.kul.database.lecturerlessons.domain.UpdateOrAddLecturerLesson;
import com.kul.database.lecturerlessons.domain.areaofstudy.AreaOfStudy;
import com.kul.database.lecturerlessons.domain.lessontype.LessonType;
import com.kul.database.usermanagement.domain.User;

public class UpdateOrAddLecturerLessonMapper {
    public static UpdateOrAddLecturerLesson toAreaOfStudyDomain(UpdateLecturerLessonRequest request) {
        return new UpdateOrAddLecturerLesson(
                request.getUserId(),
                request.getLessonName(),
                toAreaOfStudyDomain(request.getArea(), request.getDepartment()),
                Semester.valueOf(request.getSemester()),
                StudyYear.valueOf(request.getYear()),
                LessonType.newLessonType(request.getLessonType())
        );
    }

    public static UpdateLecturerLessonResponse fromDomain(LecturerLessons lecturerLessons) {
        final User user = lecturerLessons.getUser();
        return new UpdateLecturerLessonResponse(
                lecturerLessons.getId(),
                user.getId(),
                user.getFirstName() + " " + user.getLastName(),
                lecturerLessons.getLessonName(),
                toAreaOfStudyResponse(lecturerLessons.getAreaOfStudy()),
                lecturerLessons.getSemester(),
                lecturerLessons.getYear(),
                lecturerLessons.getLessonType()
        );
    }

    private static AreaOfStudy toAreaOfStudyDomain(String area, String department) {
        return AreaOfStudy.newForDepartmentAndArea(department, area);
    }

    private static AreaOfStudyResponse toAreaOfStudyResponse(AreaOfStudy areaOfStudy) {
        return new AreaOfStudyResponse(
                areaOfStudy.getId(),
                areaOfStudy.getArea(),
                areaOfStudy.getDepartment()
        );
    }
}
