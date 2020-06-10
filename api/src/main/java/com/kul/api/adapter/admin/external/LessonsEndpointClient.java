package com.kul.api.adapter.admin.external;

import com.kul.api.adapter.admin.areaofstudies.AddAreaOfStudies;
import com.kul.api.adapter.admin.areaofstudies.AddAreaOfStudiesResponse;
import com.kul.api.adapter.admin.areaofstudies.FetchAllAreaOfStudiesResponse;
import com.kul.api.adapter.admin.lessons.FetchAllLessonsResponse;
import com.kul.api.adapter.admin.lessons.UpdateLecturerLessonResponse;
import com.kul.api.adapter.admin.lessons.UpdateOrAddLecturerRequest;
import com.kul.api.adapter.admin.lessontypes.FetchAllLessonTypesResponse;
import com.kul.api.domain.admin.lessontypes.LessonTypes;
import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface LessonsEndpointClient {
    @RequestLine("PUT /lessons/area-of-studies")
    @Headers("Content-Type: application/json")
    AddAreaOfStudiesResponse addNewAreaOfStudies(AddAreaOfStudies addAreaOfStudies);

    @RequestLine("GET /lessons/area-of-studies")
    @Headers("Content-Type: application/json")
    FetchAllAreaOfStudiesResponse getAreaOfStudies();

    @RequestLine("DELETE /lessons/area-of-studies/{area}/{department}")
    @Headers("Content-Type: application/json")
    void deleteAreaOfStudies(@Param("area") String area, @Param("department") String department);

    @RequestLine("DELETE /lessons/types/{lessonType}")
    @Headers("Content-Type: application/json")
    void deleteLessonType(@Param("lessonType") String lessonType);

    @RequestLine("GET /lessons/types")
    @Headers("Content-Type: application/json")
    FetchAllLessonTypesResponse getLessonTypes();


    @RequestLine("POST /lessons/types")
    @Headers("Content-Type: application/json")
    LessonTypes addNewLessonType(LessonTypes lessonType);

    @RequestLine("DELETE /lessons/delete/{id}")
    @Headers("Content-Type: application/json")
    void deleteLessonById(@Param("id") Long id);

    @RequestLine("GET /lessons/fetch/all")
    @Headers("Content-Type: application/json")
    FetchAllLessonsResponse fetchAllLessons();

    @RequestLine("PUT /lessons/update")
    @Headers("Content-Type: application/json")
    UpdateLecturerLessonResponse updateOrAddLesson(UpdateOrAddLecturerRequest request);
}
