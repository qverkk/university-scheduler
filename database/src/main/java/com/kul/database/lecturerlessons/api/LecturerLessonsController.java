package com.kul.database.lecturerlessons.api;

import com.kul.database.lecturerlessons.api.model.areaofstudies.AddOrUpdateAreaOfStudiesRequest;
import com.kul.database.lecturerlessons.api.model.areaofstudies.AddOrUpdateAreaOfStudiesResponse;
import com.kul.database.lecturerlessons.api.model.areaofstudies.FetchAreaOfStudiesResponse;
import com.kul.database.lecturerlessons.api.model.lessons.*;
import com.kul.database.lecturerlessons.api.model.lessontypes.AddLessonTypeRequest;
import com.kul.database.lecturerlessons.api.model.lessontypes.AddLessonTypeResponse;
import com.kul.database.lecturerlessons.api.model.lessontypes.FetchAllLessonTypesResponse;
import com.kul.database.lecturerlessons.domain.AreaOfStudy;
import com.kul.database.lecturerlessons.domain.LecturerLessons;
import com.kul.database.lecturerlessons.domain.LecturerLessonsService;
import com.kul.database.lecturerlessons.domain.lessontype.LessonType;
import com.kul.database.lecturerlessons.domain.lessontype.LessonTypeService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/lessons")
public class LecturerLessonsController {

    private final LecturerLessonsService lecturerLessonsService;
    private final LessonTypeService lessonTypeService;

    public LecturerLessonsController(LecturerLessonsService lecturerLessonsService, LessonTypeService lessonTypeService) {
        this.lecturerLessonsService = lecturerLessonsService;
        this.lessonTypeService = lessonTypeService;
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
            value = "/area-of-studies"
    )
    public FetchAreaOfStudiesResponse fetchAreaOfStudies() {
        List<AreaOfStudy> allAreaOfStudies = lecturerLessonsService.findAllAreaOfStudies();
        return new FetchAreaOfStudiesResponse(allAreaOfStudies);
    }

    @PostMapping(
            value = "/area-of-studies"
    )
    public AddOrUpdateAreaOfStudiesResponse addAreaOfStudies(AddOrUpdateAreaOfStudiesRequest request) {
        return null;
    }

    @GetMapping(
            value = "/types"
    )
    public FetchAllLessonTypesResponse fetchLessonTypes() {
        List<LessonType> lessonTypes = lessonTypeService.fetchAllLessonTypes();
        return new FetchAllLessonTypesResponse(lessonTypes);
    }

    @PostMapping(
            value = "/types"
    )
    public AddLessonTypeResponse addLessonType(AddLessonTypeRequest request) {
        LessonType lessonType = lessonTypeService.addLessonType(request);
        return new AddLessonTypeResponse(lessonType);
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
