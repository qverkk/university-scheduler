package com.kul.database.lecturerlessons.domain;

import com.kul.database.usermanagement.domain.User;

import java.util.List;
import java.util.Optional;

public interface LecturerLessonsRepository {
    Optional<LecturerLessons> findByLessonNameAndUser(String lessonName, User user);
    LecturerLessons save(LecturerLessons lecturerLessons);
    void deleteAllByUserId(Long id);
    List<LecturerLessons> findAllLessons();
    List<LecturerLessons> findAllLessonsByUser(User user);
    void deleteById(Long id);
    Optional<LecturerLessons> findById(Long id);
}
