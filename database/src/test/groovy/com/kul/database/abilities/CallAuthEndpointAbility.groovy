package com.kul.database.abilities

import com.kul.database.abilities.dtos.NewUserRequest
import com.kul.database.abilities.dtos.UserLoginRequest
import io.restassured.RestAssured
import io.restassured.response.ValidatableResponse
import org.springframework.http.MediaType

trait CallAuthEndpointAbility extends RequestLocalServerAbility {
    ValidatableResponse registerNewUser(NewUserRequest request) {
        return RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(objectMapper().writeValueAsString(request))
                .post(baseUrl().resolve("/auth/register"))
                .then()
    }

    ValidatableResponse authenticateUser(UserLoginRequest request) {
        return RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(objectMapper().writeValueAsString(request))
                .post(baseUrl().resolve("/auth/auth"))
                .then()
    }

    String getAdminToken() {
        return authenticateUser(
                new UserLoginRequest()
                        .withPassword("admin")
                        .withUsername("admin@admin.com")
        ).extract().body().jsonPath().get("token").toString()
    }
}
