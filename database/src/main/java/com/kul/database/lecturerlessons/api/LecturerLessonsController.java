package com.kul.database.lecturerlessons.api;

import com.kul.database.lecturerlessons.api.model.areaofstudies.AddOrUpdateAreaOfStudiesRequest;
import com.kul.database.lecturerlessons.api.model.areaofstudies.AddOrUpdateAreaOfStudiesResponse;
import com.kul.database.lecturerlessons.api.model.areaofstudies.FetchAreaOfStudiesResponse;
import com.kul.database.lecturerlessons.api.model.lessons.*;
import com.kul.database.lecturerlessons.api.model.lessontypes.AddLessonTypeRequest;
import com.kul.database.lecturerlessons.api.model.lessontypes.AddLessonTypeResponse;
import com.kul.database.lecturerlessons.api.model.lessontypes.FetchAllLessonTypesResponse;
import com.kul.database.lecturerlessons.domain.areaofstudy.AreaOfStudy;
import com.kul.database.lecturerlessons.domain.LecturerLessons;
import com.kul.database.lecturerlessons.domain.LecturerLessonsService;
import com.kul.database.lecturerlessons.domain.areaofstudy.AreaOfStudyService;
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
    private final AreaOfStudyService areaOfStudyService;

    public LecturerLessonsController(LecturerLessonsService lecturerLessonsService, LessonTypeService lessonTypeService, AreaOfStudyService areaOfStudyService) {
        this.lecturerLessonsService = lecturerLessonsService;
        this.lessonTypeService = lessonTypeService;
        this.areaOfStudyService = areaOfStudyService;
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/update"
    )
    public UpdateLecturerLessonResponse updateLecturerLesson(@RequestBody UpdateLecturerLessonRequest request) {
        final LecturerLessons lecturerLessons = lecturerLessonsService.updateOrAddLessonForLecturer(
                UpdateOrAddLecturerLessonMapper.toAreaOfStudyDomain(request)
        );
        return UpdateOrAddLecturerLessonMapper.fromDomain(lecturerLessons);
    }

    @GetMapping(
            value = "/area-of-studies"
    )
    public FetchAreaOfStudiesResponse fetchAreaOfStudies() {
        List<AreaOfStudy> allAreaOfStudies = areaOfStudyService.findAllAreaOfStudies();
        return new FetchAreaOfStudiesResponse(allAreaOfStudies);
    }

    @RequestMapping(
            value = "/area-of-studies",
            method = RequestMethod.PUT
    )
    public AddOrUpdateAreaOfStudiesResponse addAreaOfStudies(@RequestBody AddOrUpdateAreaOfStudiesRequest request) {
        AreaOfStudy newOrUpdateAreaOfStudy = AreaOfStudy.newForDepartmentAndArea(request.getDepartment(), request.getArea());
        AreaOfStudy areaOfStudy = areaOfStudyService.addOrUpdateAreaOfStudy(newOrUpdateAreaOfStudy);
        return new AddOrUpdateAreaOfStudiesResponse(areaOfStudy);
    }

    @DeleteMapping(
            value = "/area-of-studies/{area}/{department}"
    )
    public void deleteAreaOfStudy(@PathVariable String area, @PathVariable String department) {
        areaOfStudyService.delete(area, department);
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
    public AddLessonTypeResponse addLessonType(@RequestBody AddLessonTypeRequest request) {
        LessonType lessonType = lessonTypeService.addLessonType(request);
        return new AddLessonTypeResponse(lessonType);
    }

    @DeleteMapping(
            value = "/types/{lessonName}"
    )
    public void deleteLessonType(@PathVariable String lessonName) {
        lessonTypeService.deleteById(lessonName);
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
