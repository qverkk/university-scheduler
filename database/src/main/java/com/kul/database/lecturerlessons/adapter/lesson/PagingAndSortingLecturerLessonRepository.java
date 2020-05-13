package com.kul.database.lecturerlessons.adapter.lesson;

import com.kul.database.usermanagement.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PagingAndSortingLecturerLessonRepository extends PagingAndSortingRepository<LecturerLessonEntity, Long> {
    Page<LecturerLessonEntity> findAllByUser(User user, Pageable pageable);
}
