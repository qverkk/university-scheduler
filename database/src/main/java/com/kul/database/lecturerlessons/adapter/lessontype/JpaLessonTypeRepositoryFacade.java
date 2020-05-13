package com.kul.database.lecturerlessons.adapter.lessontype;

import com.kul.database.lecturerlessons.domain.exceptions.NoSuchLessonType;
import com.kul.database.lecturerlessons.domain.lessontype.LessonType;
import com.kul.database.lecturerlessons.domain.lessontype.LessonTypeRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaLessonTypeRepositoryFacade implements LessonTypeRepository {

    private final JpaLessonTypeRepository lessonTypeRepository;
    private final PagingAndSortingLessonTypeRepository pagingAndSortingLessonTypeRepository;

    public JpaLessonTypeRepositoryFacade(JpaLessonTypeRepository lessonTypeRepository, PagingAndSortingLessonTypeRepository pagingAndSortingLessonTypeRepository) {
        this.lessonTypeRepository = lessonTypeRepository;
        this.pagingAndSortingLessonTypeRepository = pagingAndSortingLessonTypeRepository;
    }

    @Override
    public Optional<LessonType> findByLessonTypeName(String lessonType) {
        return pagingAndSortingLessonTypeRepository.findByType(lessonType, Pageable.unpaged()).stream()
                .map(LessonTypeEntityMapper::toDomain)
                .findFirst();
    }

    @Override
    public List<LessonType> findAllLessonTypes() {
        return pagingAndSortingLessonTypeRepository.findAll(Pageable.unpaged()).stream()
                .map(LessonTypeEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public LessonType save(LessonType lessonType) {
        LessonTypeEntity lessonTypeEntity = lessonTypeRepository.save(LessonTypeEntityMapper.fromDomain(lessonType));
        return LessonTypeEntityMapper.toDomain(lessonTypeEntity);
    }

    @Override
    public void delete(String lessonType) {
        LessonTypeEntity lessonTypeEntity = lessonTypeRepository.findByType(lessonType)
                .orElseThrow(() -> new NoSuchLessonType("Cannot delete " + lessonType + " as it doesnt exist"));

        lessonTypeRepository.delete(lessonTypeEntity);
    }
}
