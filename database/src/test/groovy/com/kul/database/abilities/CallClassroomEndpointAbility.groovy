package com.kul.database.abilities

import com.kul.database.abilities.dtos.NewClassroomRequest
import com.kul.database.abilities.dtos.NewClassroomTypeRequest
import com.kul.database.abilities.dtos.UserLoginRequest
import io.restassured.RestAssured
import io.restassured.response.ValidatableResponse
import org.springframework.http.MediaType

trait CallClassroomEndpointAbility extends RequestLocalServerAbility implements CallAuthEndpointAbility {
    ValidatableResponse postAuthenticatedClassroom(NewClassroomRequest request, UserLoginRequest user) {
        return RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + getUserToken(user))
                .body(objectMapper().writeValueAsString(request))
                .put(baseUrl().resolve("/classroom/add"))
                .then()
    }

    ValidatableResponse postAuthenticatedClassroomType(NewClassroomTypeRequest request, UserLoginRequest user) {
        return RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + getUserToken(user))
                .body(objectMapper().writeValueAsString(request))
                .put(baseUrl().resolve("/classroom/type/add"))
                .then()
    }
}