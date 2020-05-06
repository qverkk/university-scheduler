package com.kul.database.lecturerlessons.domain;

import com.kul.database.lecturerlessons.domain.exceptions.InsufficientPermissionsToDeleteLesson;
import com.kul.database.lecturerlessons.domain.exceptions.InsufficientPermissionsToUpdateLesson;
import com.kul.database.lecturerlessons.domain.exceptions.NoSuchLecturerLesson;
import com.kul.database.lecturerlessons.domain.exceptions.UserCannotHaveLessons;
import com.kul.database.usermanagement.domain.User;
import com.kul.database.usermanagement.domain.UserRepository;
import com.kul.database.usermanagement.domain.exceptions.NoSuchUserException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class LecturerLessonsService {

    private final LecturerLessonsRepository lecturerLessonsRepository;
    private final UserRepository userRepository;

    public LecturerLessonsService(LecturerLessonsRepository lecturerLessonsRepository, UserRepository userRepository) {
        this.lecturerLessonsRepository = lecturerLessonsRepository;
        this.userRepository = userRepository;
    }

    public List<LecturerLessons> fetchAllLecturerLessons() {
        return lecturerLessonsRepository.findAllLessons();
    }

    public LecturerLessons updateOrAddLessonForLecturer(UpdateOrAddLecturerLesson request) {
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
                request.getYear()
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
}
