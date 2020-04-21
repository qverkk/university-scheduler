package com.kul.api.adapter.admin.external;

import com.kul.api.adapter.admin.management.AllUsersResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface ManagementEndpointClient {
    @RequestLine("GET /user/all")
    @Headers("Content-Type: application/json")
    List<AllUsersResponse> getAllUsers();


    @RequestLine("POST /user/enable/{id}")
    @Headers("Content-Type: application/json")
    void enableUser(@Param("id") Long id);

    @RequestLine("POST /user/disable/{id}")
    @Headers("Content-Type: application/json")
    void disableUser(@Param("id") Long id);
}
