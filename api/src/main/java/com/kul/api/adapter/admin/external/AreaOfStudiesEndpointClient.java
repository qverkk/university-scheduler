package com.kul.api.adapter.admin.external;

import com.kul.api.adapter.admin.areaofstudies.AddAreaOfStudies;
import com.kul.api.adapter.admin.areaofstudies.AddAreaOfStudiesResponse;
import com.kul.api.adapter.admin.areaofstudies.FetchAllAreaOfStudiesResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface AreaOfStudiesEndpointClient {
    @RequestLine("PUT /lessons/area-of-studies")
    @Headers("Content-Type: application/json")
    AddAreaOfStudiesResponse addNewAreaOfStudies(AddAreaOfStudies addAreaOfStudies);

    @RequestLine("GET /lessons/area-of-studies")
    @Headers("Content-Type: application/json")
    FetchAllAreaOfStudiesResponse getAreaOfStudies();

    @RequestLine("DELETE /lessons/area-of-studies/{area}/{department}")
    @Headers("Content-Type: application/json")
    void deleteAreaOfStudies(@Param("area") String area, @Param("department") String department);
}
