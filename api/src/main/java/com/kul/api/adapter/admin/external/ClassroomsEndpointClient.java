package com.kul.api.adapter.admin.external;

import com.kul.api.adapter.admin.classrooms.AddClassroom;
import com.kul.api.adapter.admin.classrooms.AllClassroomsResponse;
import com.kul.api.adapter.admin.classroomtypes.AddClassroomType;
import com.kul.api.adapter.admin.classroomtypes.AddNewClassroomTypeResponse;
import com.kul.api.adapter.admin.classroomtypes.AllClassroomTypesResponse;
import com.kul.api.domain.admin.classroomtypes.Classrooms;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface ClassroomsEndpointClient {
    @RequestLine("GET /classroom/types")
    @Headers("Content-Type: application/json")
    List<AllClassroomTypesResponse> getAllClassroomTypes();

    @RequestLine("DELETE /classroom/delete/type/{id}")
    @Headers("Content-Type: application/json")
    void deleteClassroomTypeById(@Param("id") Long id);

    @RequestLine("PUT /classroom/type/add")
    @Headers("Content-Type: application/json")
    AddNewClassroomTypeResponse addNewClassroomType(AddClassroomType addClassroomType);

    @RequestLine("PUT /classroom/add")
    @Headers("Content-Type: application/json")
    void addNewClassroom(AddClassroom addClassroom);

    @RequestLine("GET /classroom/classrooms")
    @Headers("Content-Type: application/json")
    List<AllClassroomsResponse> getAllClassrooms();

    @RequestLine("DELETE /classroom/delete/classroom/{id}")
    @Headers("Content-Type: application/json")
    void deleteClassroomById(@Param("id") long id);
}
