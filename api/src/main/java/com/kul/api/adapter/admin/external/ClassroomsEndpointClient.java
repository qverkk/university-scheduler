package com.kul.api.adapter.admin.external;

import com.kul.api.adapter.admin.classroomtypes.AllClassroomTypesResponse;
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
}
