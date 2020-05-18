package com.kul.database.currentsubjects.adapter;

import com.kul.database.classrooms.adapter.classroom.ClassroomEntity;
import com.kul.database.classrooms.adapter.classroom.ClassroomEntityMapper;
import com.kul.database.classrooms.domain.classroom.Classroom;
import com.kul.database.currentsubjects.domain.CurrentSubjects;
import com.kul.database.currentsubjects.domain.CurrentSubjectsRepository;
import com.kul.database.lecturerlessons.adapter.lesson.LecturerLessonEntity;
import com.kul.database.lecturerlessons.adapter.lesson.LecturerLessonsEntityMapper;
import com.kul.database.lecturerlessons.domain.LecturerLessons;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaCurrentSubjectsRepositoryFacade implements CurrentSubjectsRepository {

    private final JpaCurrentSubjectsRepository currentSubjectsRepository;

    public JpaCurrentSubjectsRepositoryFacade(JpaCurrentSubjectsRepository currentSubjectsRepository) {
        this.currentSubjectsRepository = currentSubjectsRepository;
    }

    @Override
    public CurrentSubjects save(CurrentSubjects currentSubjects) {
        final CurrentSubjectsEntity currentSubjectsEntity = CurrentSubjectsEntityMapper.fromDomain(currentSubjects);
        final CurrentSubjectsEntity saved = currentSubjectsRepository.save(currentSubjectsEntity);
        return CurrentSubjectsEntityMapper.toDomain(saved);
    }

    @Override
    public List<CurrentSubjects> fetchAll() {
        return currentSubjectsRepository.findAll().stream()
                .map(CurrentSubjectsEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CurrentSubjects> findByClassRoomAndLesson(Classroom classroom, LecturerLessons lecturerLessons) {
        final ClassroomEntity classroomEntity = ClassroomEntityMapper.fromDomain(classroom);
        final LecturerLessonEntity lecturerLessonEntity = LecturerLessonsEntityMapper.fromDomain(lecturerLessons);
        return currentSubjectsRepository.findByClassroomEntityAndLecturerLessonEntity(
                classroomEntity,
                lecturerLessonEntity
        ).map(CurrentSubjectsEntityMapper::toDomain);
    }
}
