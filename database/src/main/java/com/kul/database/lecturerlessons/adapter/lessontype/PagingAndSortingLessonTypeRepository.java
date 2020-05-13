package com.kul.database.lecturerlessons.adapter.lessontype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PagingAndSortingLessonTypeRepository extends PagingAndSortingRepository<LessonTypeEntity, Long> {
    Page<LessonTypeEntity> findByType(String lessonType, Pageable pageable);
}
