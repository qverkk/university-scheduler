package com.kul.api.domain.admin.areaofstudies;

import com.kul.api.domain.admin.lessontypes.LessonTypes;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class LessonsManagement {
    private final LessonsRepository lessonsRepository;

    public void addNewAreaOfStudies(AreaOfStudies newAreaOfStudies) {
        lessonsRepository.addNewAreaOfStudies(newAreaOfStudies);
    }

    public List<AreaOfStudies> getAllAreaOfStudies() {
        return lessonsRepository.getAllAreaOfStudies();
    }

    public void removeAreaOfStudies(String area, String department) {
        lessonsRepository.removeAreaOfStudies(area, department);
    }

    public void removeLessonType(String typeName) {
        lessonsRepository.removeLessonType(typeName);
    }

    public List<LessonTypes> getAllLessonTypes() {
        return lessonsRepository.getAllLessonTypes();
    }

    public void addNewLessonType(LessonTypes lessonType) {
        lessonsRepository.addNewLessonType(lessonType);
    }
}
