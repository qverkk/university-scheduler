package com.kul.database.lecturerlessons.api.model.lessons;

import com.kul.database.lecturerlessons.api.model.areaofstudies.AreaOfStudyResponse;
import com.kul.database.lecturerlessons.domain.LecturerLessons;
import com.kul.database.lecturerlessons.domain.UpdateOrAddLecturerLesson;
import com.kul.database.lecturerlessons.domain.areaofstudy.AreaOfStudy;

public class UpdateOrAddLecturerLessonMapper {
    public static UpdateOrAddLecturerLesson toAreaOfStudyDomain(UpdateLecturerLessonRequest request) {
        return new UpdateOrAddLecturerLesson(
                request.getUserId(),
                request.getLessonName(),
                toAreaOfStudyDomain(request.getArea(), request.getDepartment()),
                request.getSemester(),
                request.getYear(),
                request.getLessonType()
        );
    }

    public static UpdateLecturerLessonResponse fromDomain(LecturerLessons lecturerLessons) {
        return new UpdateLecturerLessonResponse(
                lecturerLessons.getId(),
                lecturerLessons.getUser().getId(),
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
