package com.kul.database.lecturerlessons.adapter.lesson;

import com.kul.database.lecturerlessons.domain.LecturerLessons;
import com.kul.database.lecturerlessons.domain.LecturerLessonsRepository;
import com.kul.database.usermanagement.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaLecturerLessonRepositoryFacade implements LecturerLessonsRepository {

    private final JpaLecturerLessonRepository lecturerLessonRepository;
    private final PagingAndSortingLecturerLessonRepository pagingAndSortingLecturerLessonRepository;

    public JpaLecturerLessonRepositoryFacade(JpaLecturerLessonRepository lecturerLessonRepository, PagingAndSortingLecturerLessonRepository pagingAndSortingLecturerLessonRepository) {
        this.lecturerLessonRepository = lecturerLessonRepository;
        this.pagingAndSortingLecturerLessonRepository = pagingAndSortingLecturerLessonRepository;
    }

    @Override
    public Optional<LecturerLessons> findByLessonNameAndUser(String lessonName, User user) {
        return lecturerLessonRepository.findByLessonNameAndUser(lessonName, user)
                .map(LecturerLessonsEntityMapper::toDomain);
    }

    @Override
    public LecturerLessons save(LecturerLessons lecturerLessons) {
        LecturerLessonEntity entity = LecturerLessonsEntityMapper.fromDomain(lecturerLessons);

        LecturerLessonEntity saved = lecturerLessonRepository.save(entity);

        return LecturerLessonsEntityMapper.toDomain(saved);
    }

    @Override
    public void deleteAllByUserId(Long userId) {
        lecturerLessonRepository.deleteAllByUserId(userId);
    }

    @Override
    public List<LecturerLessons> findAllLessons() {
        return pagingAndSortingLecturerLessonRepository.findAll(Pageable.unpaged()).stream()
                .map(LecturerLessonsEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<LecturerLessons> findAllLessonsByUser(User user) {
        return pagingAndSortingLecturerLessonRepository.findAllByUser(user, Pageable.unpaged()).stream()
                .map(LecturerLessonsEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        lecturerLessonRepository.deleteById(id);
    }

    @Override
    public Optional<LecturerLessons> findById(Long id) {
        return lecturerLessonRepository.findById(id)
                .map(LecturerLessonsEntityMapper::toDomain);
    }
}
