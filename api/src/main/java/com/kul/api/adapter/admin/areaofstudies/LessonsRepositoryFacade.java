package com.kul.api.adapter.admin.areaofstudies;

import com.kul.api.adapter.admin.external.LessonsEndpointClient;
import com.kul.api.adapter.admin.external.ErrorResponseException;
import com.kul.api.adapter.admin.lessons.UpdateLecturerLessonResponse;
import com.kul.api.adapter.admin.lessons.UpdateOrAddLecturerRequest;
import com.kul.api.domain.admin.areaofstudies.AreaOfStudies;
import com.kul.api.domain.admin.areaofstudies.LessonsRepository;
import com.kul.api.domain.admin.lessons.Lessons;
import com.kul.api.domain.admin.lessontypes.LessonTypes;

import java.util.List;
import java.util.stream.Collectors;

public class LessonsRepositoryFacade implements LessonsRepository {

    private final LessonsEndpointClient client;

    public LessonsRepositoryFacade(LessonsEndpointClient client) {
        this.client = client;
    }

    @Override
    public void addNewAreaOfStudies(AreaOfStudies newAreaOfStudies) {
        try {
            client.addNewAreaOfStudies(
                    new AddAreaOfStudies(
                            newAreaOfStudies.getAreaOfStudiesName(),
                            newAreaOfStudies.getDepartmentName()
                    )
            );
        } catch (ErrorResponseException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<AreaOfStudies> getAllAreaOfStudies() {
        return client.getAreaOfStudies().getAreaOfStudies().stream().map(areas -> new AreaOfStudies(
                areas.getId(),
                areas.getArea(),
                areas.getDepartment()
        )).collect(Collectors.toList());
    }

    @Override
    public void removeAreaOfStudies(String area, String department) {
        client.deleteAreaOfStudies(area, department);
    }

    @Override
    public void removeLessonType(String typeName) {
        client.deleteLessonType(typeName);
    }

    @Override
    public List<LessonTypes> getAllLessonTypes() {
        return client.getLessonTypes().getLessonTypes().stream().map(types -> new LessonTypes(
                types.getId(),
                types.getType()
        )).collect(Collectors.toList());
    }

    @Override
    public LessonTypes addNewLessonType(LessonTypes lessonType) {
        return client.addNewLessonType(lessonType);
    }

    @Override
    public void removeLessonById(Long id) {
        client.deleteLessonById(id);
    }

    @Override
    public List<Lessons> getAllLessons() {
        return client.fetchAllLessons().getLecturerLessons().stream().map(lessons -> new Lessons(
                lessons.getId(),
                lessons.getUserId(),
                lessons.getLecturersName(),
                lessons.getLessonName(),
                lessons.getAreaOfStudy(),
                lessons.getLessonType(),
                lessons.getSemester(),
                lessons.getYear()
        )).collect(Collectors.toList());
    }

    @Override
    public Lessons addnewLesson(Lessons result) {
        final UpdateLecturerLessonResponse updateOrAddResult = client.updateOrAddLesson(
                new UpdateOrAddLecturerRequest(
                        result.getUserId(),
                        result.getLessonName(),
                        result.getAreaOfStudy().getArea(),
                        result.getAreaOfStudy().getDepartment(),
                        result.getLessonType().getType(),
                        result.getSemester().toString(),
                        result.getYear().toString()
                )
        );
        return new Lessons(
                updateOrAddResult.getId(),
                updateOrAddResult.getUserId(),
                updateOrAddResult.getLecturersName(),
                updateOrAddResult.getLessonName(),
                updateOrAddResult.getAreaOfStudy(),
                updateOrAddResult.getLessonType(),
                updateOrAddResult.getSemester(),
                updateOrAddResult.getYear()
        );
    }
}
