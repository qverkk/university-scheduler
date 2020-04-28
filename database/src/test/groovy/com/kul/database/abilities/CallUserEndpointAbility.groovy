package com.kul.database.abilities

import io.restassured.RestAssured
import io.restassured.response.ValidatableResponse

trait CallUserEndpointAbility extends RequestLocalServerAbility {
    ValidatableResponse enableUser(Long id, String token) {
        return RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .post(baseUrl().resolve("/user/enable/" + id))
                .then()
    }

    ValidatableResponse disableUser(Long id, String token) {
        return RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .post(baseUrl().resolve("/user/disable/" + id))
                .then()
    }
}
