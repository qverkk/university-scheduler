package com.kul.api.domain.admin.areaofstudies;

import com.kul.api.domain.admin.lessontypes.LessonTypes;

import java.util.List;

public interface LessonsRepository {
    void addNewAreaOfStudies(AreaOfStudies newAreaOfStudies);

    List<AreaOfStudies> getAllAreaOfStudies();

    void removeAreaOfStudies(String area, String department);

    void removeLessonType(String typeName);

    List<LessonTypes> getAllLessonTypes();

    LessonTypes addNewLessonType(LessonTypes lessonType);
}
