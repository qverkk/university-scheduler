package com.kul.database.lecturerlessons;

import com.kul.database.lecturerlessons.api.model.*;
import com.kul.database.lecturerlessons.domain.AreaOfStudy;
import com.kul.database.lecturerlessons.domain.LecturerLessons;
import com.kul.database.lecturerlessons.domain.LecturerLessonsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/lessons")
public class LecturerLessonsController {

    private final LecturerLessonsService lecturerLessonsService;

    public LecturerLessonsController(LecturerLessonsService lecturerLessonsService) {
        this.lecturerLessonsService = lecturerLessonsService;
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/update"
    )
    public UpdateLecturerLessonResponse updateLecturerLesson(@RequestBody UpdateLecturerLessonRequest request) {
        final LecturerLessons lecturerLessons = lecturerLessonsService.updateOrAddLessonForLecturer(
                UpdateOrAddLecturerLessonMapper.toDomain(request)
        );
        return UpdateOrAddLecturerLessonMapper.fromDomain(lecturerLessons);
    }

    @GetMapping(
            value = "/fetch/area-of-studies"
    )
    public FetchAreaOfStudiesResponse fetchAreaOfStudies() {
        return new FetchAreaOfStudiesResponse(Arrays.asList(AreaOfStudy.values()));
    }

    @GetMapping(
            value = "/fetch/all"
    )
    public FetchAllLecturerLessonsResponse fetchAllLecturerLessons() {
        return new FetchAllLecturerLessonsResponse(lecturerLessonsService.fetchAllLecturerLessons());
    }

    @GetMapping(
            value = "/fetch/{userId}"
    )
    public FetchLecturerLessonsResponse fetchLecturerLessons(@PathVariable Long userId) {
        List<LecturerLessons> lecturerLessons = lecturerLessonsService.fetchAllLecturerLessons(userId);
        return new FetchLecturerLessonsResponse(lecturerLessons);
    }

    @DeleteMapping(
            value = "/delete/{id}"
    )
    public void deleteLecturerLessons(Principal principal, @PathVariable Long id) {
        lecturerLessonsService.deleteLecturerLessonById(principal, id);
    }
}
