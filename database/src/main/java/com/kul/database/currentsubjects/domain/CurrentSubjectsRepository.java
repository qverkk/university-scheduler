package com.kul.database.currentsubjects.domain;

import com.kul.database.classrooms.domain.classroom.Classroom;
import com.kul.database.lecturerlessons.domain.LecturerLessons;

import java.util.List;
import java.util.Optional;

public interface CurrentSubjectsRepository {

    CurrentSubjects save(CurrentSubjects currentSubjects);

    List<CurrentSubjects> fetchAll();

    Optional<CurrentSubjects> findByClassRoomAndLesson(Classroom classroom, LecturerLessons lecturerLessons);

    void deleteById(Long id);
}
