package com.kul.api.adapter.admin.external;

import com.kul.api.adapter.admin.management.lecturer.preferences.FetchLecturerPreferenceResponse;
import com.kul.api.adapter.admin.management.lecturer.preferences.LecturerPreferencesRequest;
import com.kul.api.adapter.admin.management.lecturer.preferences.LecturerPreferencesResponse;
import com.kul.api.adapter.admin.management.users.AllUsersResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.time.DayOfWeek;
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

    @RequestLine("PUT /preferences/update")
    @Headers("Content-Type: application/json")
    LecturerPreferencesResponse updatePreferences(LecturerPreferencesRequest preferences);

    @RequestLine("GET /preferences/fetch/{userId}/{day}")
    @Headers("Content-Type: application/json")
    FetchLecturerPreferenceResponse fetchPreferences(@Param("userId") Long userId, @Param("day") DayOfWeek day);
}
