package com.kul.database

import com.kul.database.abilities.CallAuthEndpointAbility
import com.kul.database.abilities.CallLecturerPreferencesEndpointAbility
import com.kul.database.abilities.CallUserEndpointAbility
import spock.lang.Shared

import static com.kul.database.abilities.dtos.NewLecturerPreferencesRequests.aExistingLecturerPreference
import static com.kul.database.abilities.dtos.NewLecturerPreferencesRequests.aNewLecturerPreference
import static com.kul.database.abilities.dtos.NewUserRequests.aDisabledDeaneryUser
import static com.kul.database.abilities.dtos.UserLoginRequests.aUser
import static org.hamcrest.Matchers.contains
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.hasSize
import static org.springframework.http.HttpStatus.FORBIDDEN
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

class LecturerPreferencesControllerSpec extends BaseIntegrationSpec
        implements CallLecturerPreferencesEndpointAbility, CallAuthEndpointAbility, CallUserEndpointAbility {

    @Shared def token = ""

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

    def "should respond 200 when getting admin token"() {
        given:
            def user = aUser("admin@admin.com", "admin")

        when:
            def tokenResponse = authenticateUser(user)
            token = tokenResponse.extract().body().jsonPath().get("token").toString()

        then:
            tokenResponse.statusCode(OK.value())
    }

    def "should respond 200 when enabling user"() {
        given:
            def userId = 2L

        when:
            def response = enableUser(userId, token)

        then:
            response.statusCode(OK.value())
    }

    def "should respond 200 when adding new request"() {
        given:
            def preference = aNewLecturerPreference()

        when:
            def response = addNewPreference(preference, token)

        then:
            response.statusCode(OK.value())
                .body("lecturerPreferenceId", equalTo(1))
                .body("userId", equalTo(2))
                .body("startTime", equalTo("8:00"))
                .body("endTime", equalTo("15:00"))
                .body("day", equalTo("MONDAY"))
    }

    def "should respond 422 when adding new request"() {
        given:
            def preference = aNewLecturerPreference()

        when:
            def response = addNewPreference(preference, token)

        then:
            response.statusCode(UNPROCESSABLE_ENTITY.value())
                .body("message", equalTo(String.format("Preference for %s already exists", preference.getDay())))
    }

    def "should respond 200 when updating preferences for user"() {
        given:
            def preference = aNewLecturerPreference()
                        .withStartTime("9:00")
                        .withEndTime("14:00")

        when:
            def response = updatePreference(preference, token)

        then:
            response.statusCode(OK.value())
                .body("lecturerPreferenceId", equalTo(1))
                .body("userId", equalTo(2))
                .body("startTime", equalTo("9:00"))
                .body("endTime", equalTo("14:00"))
                .body("day", equalTo("MONDAY"))
    }

    def "should respond 422 when updating for non existent user"() {
        given:
            def preference = aNewLecturerPreference()
                    .withStartTime("9:00")
                    .withEndTime("14:00")
                    .withUserId(3)

        when:
            def response = updatePreference(preference, token)

        then:
            response.statusCode(UNPROCESSABLE_ENTITY.value())
                .body("message", equalTo("User No username provided cannot be found"))
    }
}
