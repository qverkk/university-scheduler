package com.kul.database.abilities

import io.restassured.RestAssured
import io.restassured.response.ValidatableResponse

trait CallUserEndpointAbility extends RequestLocalServerAbility implements CallAuthEndpointAbility {
    ValidatableResponse enableUser(Long id) {
        return RestAssured.given()
                .header("Authorization", "Bearer " + getAdminToken())
                .post(baseUrl().resolve("/user/enable/" + id))
                .then()
    }

    ValidatableResponse disableUser(Long id) {
        return RestAssured.given()
                .header("Authorization", "Bearer " + getAdminToken())
                .post(baseUrl().resolve("/user/disable/" + id))
                .then()
    }
}
