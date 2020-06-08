package com.kul.database.abilities

import com.kul.database.abilities.dtos.NewCurrentSubjectRequest
import com.kul.database.abilities.dtos.UserLoginRequest
import io.restassured.RestAssured
import io.restassured.response.ValidatableResponse
import org.springframework.http.MediaType

trait CallCurrentSubjectsEndpointAbility extends RequestLocalServerAbility implements CallAuthEndpointAbility {
    ValidatableResponse postAuthenticatedAddCurrentSubjectForUser(NewCurrentSubjectRequest request, UserLoginRequest user) {
        return RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + getUserToken(user))
                .body(objectMapper().writeValueAsString(request))
                .put(baseUrl().resolve("/currentsubjects/add"))
                .then()
    }

    ValidatableResponse postAuthenticatedDeleteCurrentSubjectForUser(int id, UserLoginRequest user) {
        return RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + getUserToken(user))
                .delete(baseUrl().resolve("/currentsubjects/" + id))
                .then()
    }
}