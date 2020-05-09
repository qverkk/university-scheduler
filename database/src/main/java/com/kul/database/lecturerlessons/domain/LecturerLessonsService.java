package com.kul.database.lecturerlessons.domain;

import com.kul.database.lecturerlessons.domain.exceptions.*;
import com.kul.database.lecturerlessons.domain.lessontype.LessonType;
import com.kul.database.lecturerlessons.domain.lessontype.LessonTypeRepository;
import com.kul.database.usermanagement.domain.User;
import com.kul.database.usermanagement.domain.UserRepository;
import com.kul.database.usermanagement.domain.exceptions.NoSuchUserException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class LecturerLessonsService {

    private final LecturerLessonsRepository lecturerLessonsRepository;
    private final LessonTypeRepository lessonTypeRepository;
    private final UserRepository userRepository;

    public LecturerLessonsService(LecturerLessonsRepository lecturerLessonsRepository, LessonTypeRepository lessonTypeRepository, UserRepository userRepository) {
        this.lecturerLessonsRepository = lecturerLessonsRepository;
        this.lessonTypeRepository = lessonTypeRepository;
        this.userRepository = userRepository;
    }

    public List<LecturerLessons> fetchAllLecturerLessons() {
        return lecturerLessonsRepository.findAllLessons();
    }

    public LecturerLessons updateOrAddLessonForLecturer(UpdateOrAddLecturerLesson request) {
        LessonType lessonType = lessonTypeRepository.findByLessonTypeName(request.getLessonType())
                .orElseThrow(() -> new NoSuchLessonType(request.getLessonType() + " cannot be found"));

        final User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NoSuchUserException("No username provided"));

        if (!user.canHaveLessons()) {
            throw new UserCannotHaveLessons(user.getUsername());
        }
        final LecturerLessons lesson = lecturerLessonsRepository.findByLessonNameAndUser(request.getLessonName(), user)
                .orElse(LecturerLessons.newForLessonName(user, request.getLessonName()));

        if (!lesson.canBeUpdatedBy(user)) {
            throw new InsufficientPermissionsToUpdateLesson(
                    "Only admin, dziekanat or user for this permission can update it"
            );
        }

        lesson.changeLessonDetails(
                request.getAreaOfStudy(),
                request.getSemester(),
                request.getYear(),
                lessonType
        );

        return lecturerLessonsRepository.save(lesson);
    }

    public List<LecturerLessons> fetchAllLecturerLessons(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchUserException("No username provided"));

        return lecturerLessonsRepository.findAllLessonsByUser(user);
    }

    public void deleteLecturerLessonById(Principal principal, Long id) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchUserException(username));

        LecturerLessons lesson = lecturerLessonsRepository.findById(id)
                .orElseThrow(() -> new NoSuchLecturerLesson(
                        id + " does not exist"
                ));

        if (!lesson.canBeDeletedBy(user)) {
            throw new InsufficientPermissionsToDeleteLesson(
                    "Only admin, dziekanat and an authenticated user for this lesson can delete it"
            );
        }

        lecturerLessonsRepository.deleteById(id);
    }

    public List<AreaOfStudy> findAllAreaOfStudies() {
        // TODO: add area of studies
        return null;
    }
}
