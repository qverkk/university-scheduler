package com.kul.database.lecturerlessons.domain.lessontype;

import com.kul.database.lecturerlessons.api.model.lessontypes.AddLessonTypeRequest;
import com.kul.database.lecturerlessons.domain.exceptions.LessonTypeAlreadyExists;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<LessonType> lessonType = lessonTypeRepository.findByLessonTypeName(request.getType());
        if (lessonType.isPresent()) {
            throw new LessonTypeAlreadyExists(request.getType() + " already exists");
        }

        return lessonTypeRepository.save(LessonType.newLessonType(request.getType()));
    }

    public void deleteByLessonType(String lessonType) {
        lessonTypeRepository.delete(lessonType);
    }
}
