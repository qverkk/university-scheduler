package com.kul.database

import com.kul.database.abilities.CallAuthEndpointAbility
import com.kul.database.abilities.CallLecturerPreferencesEndpointAbility
import com.kul.database.abilities.CallUserEndpointAbility

import static com.kul.database.abilities.dtos.NewLecturerPreferencesRequests.aExistingLecturerPreference
import static com.kul.database.abilities.dtos.NewLecturerPreferencesRequests.aNewLecturerPreference
import static com.kul.database.abilities.dtos.NewUserRequests.aDisabledDeaneryUser
import static com.kul.database.abilities.dtos.UserLoginRequests.aUser
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.hasSize
import static org.springframework.http.HttpStatus.FORBIDDEN
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

class LecturerPreferencesControllerSpec extends BaseIntegrationSpec
        implements CallLecturerPreferencesEndpointAbility, CallAuthEndpointAbility, CallUserEndpointAbility {

    def adminToken = ""

    def "should respond 200 when registering new account"() {
        given:
            def user = aDisabledDeaneryUser()

        when:
            def response = registerNewUser(user)

        then:
            response.statusCode(OK.value())
                    .body("newUserAssignedId", equalTo(2))
                    .body("success", equalTo(true))
    }

    def "should respond 200 when enabling user"() {
        given:
            def user = aUser("admin@admin.com", "admin")

        when:
            def tokenResponse = authenticateUser(user)
            tokenResponse.statusCode(OK.value())
            adminToken = tokenResponse.extract().body().jsonPath().get("token").toString()
            def response = enableUser(2L, adminToken)

        then:
            response.statusCode(OK.value())
    }

    def "should respond 204 when adding new request"() {
        // TODO: FIX THIS, getting 403
        given:
            def preference = aNewLecturerPreference()

        when:
            def response = addNewPreference(preference, adminToken)

        then:
            response.statusCode(NO_CONTENT.value())
    }
}
