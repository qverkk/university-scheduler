package com.kul.api.adapter.admin.external;

import com.kul.api.adapter.admin.management.AllUsersResponse;
import feign.Headers;
import feign.RequestLine;

import java.util.List;

public interface ManagementEndpointClient {
    @RequestLine("GET /user/all")
    @Headers("Content-Type: application/json")
    List<AllUsersResponse> getAllUsers();
}
