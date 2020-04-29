package com.kul.database.abilities

import com.kul.database.abilities.dtos.NewLecturerPreferencesRequest
import io.restassured.RestAssured
import io.restassured.response.ValidatableResponse
import org.springframework.http.MediaType

trait CallLecturerPreferencesEndpointAbility extends RequestLocalServerAbility implements CallAuthEndpointAbility {
    ValidatableResponse postAuthenticatedToPreferencesUpdate(NewLecturerPreferencesRequest request) {
        return RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + getAdminToken())
                .body(objectMapper().writeValueAsString(request))
                .put(baseUrl().resolve("/preferences/update"))
                .then()
    }

    ValidatableResponse postAuthenticatedToPreferencesAdd(NewLecturerPreferencesRequest request) {
        return RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + getAdminToken())
                .body(objectMapper().writeValueAsString(request))
                .post(baseUrl().resolve("/preferences/add"))
                .then()
    }
}
