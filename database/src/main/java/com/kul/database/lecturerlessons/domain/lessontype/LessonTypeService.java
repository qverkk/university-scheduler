package com.kul.database.lecturerlessons.domain.lessontype;

import com.kul.database.lecturerlessons.api.model.lessontypes.AddLessonTypeRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonTypeService {

    private final LessonTypeRepository lessonTypeRepository;

    public LessonTypeService(LessonTypeRepository lessonTypeRepository) {
        this.lessonTypeRepository = lessonTypeRepository;
    }

    public List<LessonType> fetchAllLessonTypes() {
        return lessonTypeRepository.findAllLessonTypes();
    }

    public LessonType addLessonType(AddLessonTypeRequest request) {
        LessonType lessonType = lessonTypeRepository.findByLessonTypeName(request.getType())
                .orElse(LessonType.newLessonType(request.getType()));

        return lessonTypeRepository.save(lessonType);
    }
}
