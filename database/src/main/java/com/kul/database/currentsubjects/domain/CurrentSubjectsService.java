package com.kul.database.currentsubjects.domain;

import com.kul.database.classrooms.domain.classroom.Classroom;
import com.kul.database.classrooms.domain.classroom.ClassroomsRepository;
import com.kul.database.classrooms.domain.classroomtype.ClassroomType;
import com.kul.database.classrooms.domain.classroomtype.ClassroomTypeRepository;
import com.kul.database.classrooms.domain.exceptions.ClassroomDoesntExist;
import com.kul.database.classrooms.domain.exceptions.ClassroomTypeDoesntExist;
import com.kul.database.lecturerlessons.domain.LecturerLessons;
import com.kul.database.lecturerlessons.domain.LecturerLessonsRepository;
import com.kul.database.lecturerlessons.domain.exceptions.NoSuchLecturerLesson;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrentSubjectsService {

    private final CurrentSubjectsRepository currentSubjectsRepository;
    private final ClassroomsRepository classroomsRepository;
    private final ClassroomTypeRepository classroomTypeRepository;
    private final LecturerLessonsRepository lecturerLessonsRepository;

    public CurrentSubjectsService(CurrentSubjectsRepository currentSubjectsRepository, ClassroomsRepository classroomsRepository, ClassroomTypeRepository classroomTypeRepository, LecturerLessonsRepository lecturerLessonsRepository) {
        this.currentSubjectsRepository = currentSubjectsRepository;
        this.classroomsRepository = classroomsRepository;
        this.classroomTypeRepository = classroomTypeRepository;
        this.lecturerLessonsRepository = lecturerLessonsRepository;
    }

    public List<CurrentSubjects> fetchAllCurrentSubjects() {
        return currentSubjectsRepository.fetchAll();
    }

    public CurrentSubjects addOrUpdate(UpdateCurrentSubjects currentSubjects) {
        final List<String> classroomTypeNames = currentSubjects.getClassroom().getClassroomTypes().stream()
                .map(ClassroomType::getName)
                .collect(Collectors.toList());

        final List<ClassroomType> classroomTypes = classroomTypeRepository.findAllByNames(classroomTypeNames);
        List<String> classroomTypesNamesResult = classroomTypes.stream()
                .map(ClassroomType::getName)
                .collect(Collectors.toList());
        if (!classroomTypeNames.containsAll(classroomTypesNamesResult)) {
            classroomTypeNames.removeAll(classroomTypesNamesResult);
            String missingTypes = classroomTypeNames.stream()
                    .map(e -> e + " ")
                    .toString();
            throw new ClassroomTypeDoesntExist(missingTypes);
        }

        final Classroom classroom = classroomsRepository.findByNameAndTypes(
                currentSubjects.getClassroom().getName(),
                classroomTypes
        ).orElseThrow(() -> new ClassroomDoesntExist(currentSubjects.getClassroom().getName()));

        final LecturerLessons lecturerLessons = lecturerLessonsRepository.findById(currentSubjects.getLecturerLesson().getId())
                .orElseThrow(() -> new NoSuchLecturerLesson("for id " + currentSubjects.getLecturerLesson().getId()));

        final CurrentSubjects entity = currentSubjectsRepository.findByClassRoomAndLesson(classroom, lecturerLessons)
                .orElse(CurrentSubjects.newForClassroomAndLesson(
                        classroom,
                        lecturerLessons
                ));
        final CurrentSubjects editedEntity = entity.updateTimes(currentSubjects.getStartTime(), currentSubjects.getEndTime());
        return currentSubjectsRepository.save(
            editedEntity
        );
    }

    public void deleteById(Long id) {
        currentSubjectsRepository.deleteById(id);
    }
}
