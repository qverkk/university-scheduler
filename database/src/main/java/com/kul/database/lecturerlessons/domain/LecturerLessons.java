package com.kul.database.lecturerlessons.domain;

import com.kul.database.lecturerlessons.domain.areaofstudy.AreaOfStudy;
import com.kul.database.lecturerlessons.domain.lessontype.LessonType;
import com.kul.database.usermanagement.domain.User;
import lombok.Getter;

@Getter
public class LecturerLessons {
    private final Long id;
    private final User user;
    private final String lessonName;
    private AreaOfStudy areaOfStudy;
    private LessonType lessonType;
    private Integer semester;
    private Integer year;
    private final Long version;

    public LecturerLessons(Long id, User user, String lessonName, AreaOfStudy areaOfStudy, LessonType lessonType, Integer semester, Integer year, Long version) {
        this.id = id;
        this.user = user;
        this.lessonName = lessonName;
        this.areaOfStudy = areaOfStudy;
        this.lessonType = lessonType;
        this.semester = semester;
        this.year = year;
        this.version = version;
    }

    public static LecturerLessons newForLessonName(User user, String lessonName) {
        return new LecturerLessons(
                null,
                user,
                lessonName,
                null,
                null,
                null,
                null,
                1L
        );
    }

    public boolean canBeUpdatedBy(User user) {
        return user.canUpdateLessonOf(this.user);
    }

    public void changeLessonDetails(AreaOfStudy areaOfStudy, int semester, int year, LessonType lessonType, AreaOfStudy ofStudy) {
        this.areaOfStudy = areaOfStudy;
        this.semester = semester;
        this.year = year;
        this.lessonType = lessonType;
        this.areaOfStudy = ofStudy;
    }

    public boolean canBeDeletedBy(User user) {
        return user.canDeleteLessonOf(user);
    }
}
