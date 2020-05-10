package com.kul.database.abilities

import com.kul.database.abilities.dtos.NewAreaOfStudyRequest
import com.kul.database.abilities.dtos.NewLecturerLessonRequest
import com.kul.database.abilities.dtos.NewLecturerPreferencesRequest
import com.kul.database.abilities.dtos.NewLessonTypeRequest
import com.kul.database.abilities.dtos.NewUserRequest
import com.kul.database.abilities.dtos.NewUserRequests
import com.kul.database.abilities.dtos.UserLoginRequest
import io.restassured.RestAssured
import io.restassured.response.ValidatableResponse
import org.springframework.http.MediaType

trait CallLecturerLessonsEndpointAbility extends RequestLocalServerAbility implements CallAuthEndpointAbility {
    ValidatableResponse postAuthenticatedLecturerLessonForUser(NewLecturerLessonRequest request, UserLoginRequest user) {
        return RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + getUserToken(user))
                .body(objectMapper().writeValueAsString(request))
                .put(baseUrl().resolve("/lessons/update"))
                .then()
    }

    ValidatableResponse postAuthenticatedAddLessonType(NewLessonTypeRequest request, UserLoginRequest user) {
        return RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + getUserToken(user))
                .body(objectMapper().writeValueAsString(request))
                .post(baseUrl().resolve("/lessons/types"))
                .then()
    }

    ValidatableResponse postAuthenticatedUpdateAreaOfStudy(NewAreaOfStudyRequest request, UserLoginRequest user) {
        return RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + getUserToken(user))
                .body(objectMapper().writeValueAsString(request))
                .put(baseUrl().resolve("/lessons/area-of-studies"))
                .then()
    }
}