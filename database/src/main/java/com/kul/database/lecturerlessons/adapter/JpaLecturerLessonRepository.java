package com.kul.database.lecturerlessons.adapter;

import com.kul.database.usermanagement.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface JpaLecturerLessonRepository extends JpaRepository<LecturerLessonEntity, Long> {
    Optional<LecturerLessonEntity> findByLessonNameAndUser(String lessonName, User user);

    List<LecturerLessonEntity> findAllByUser(User user);

    @Modifying
    @Transactional
    @Query("delete from lecturer_lesson where user.id = ?1")
    void deleteAllByUserId(Long userId);
}
