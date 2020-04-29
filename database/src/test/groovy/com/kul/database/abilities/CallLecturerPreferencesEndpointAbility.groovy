package com.kul.database.abilities

import com.kul.database.abilities.dtos.NewLecturerPreferencesRequest
import io.restassured.RestAssured
import io.restassured.response.ValidatableResponse
import org.springframework.http.MediaType

trait CallLecturerPreferencesEndpointAbility extends RequestLocalServerAbility {
    ValidatableResponse addNewPreference(NewLecturerPreferencesRequest request, String token) {
        return RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token)
                .body(objectMapper().writeValueAsString(request))
                .post(baseUrl().resolve("/preferences/add"))
                .then()
    }

    ValidatableResponse updatePreference(NewLecturerPreferencesRequest request, String token) {
        return RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token)
                .body(objectMapper().writeValueAsString(request))
                .put(baseUrl().resolve("/preferences/update"))
                .then()
    }
}
