package com.kul.database


import com.kul.database.abilities.CallLecturerPreferencesEndpointAbility
import com.kul.database.abilities.CallUserEndpointAbility

import static com.kul.database.abilities.dtos.NewLecturerPreferencesRequests.aNewLecturerPreference
import static com.kul.database.abilities.dtos.NewUserRequests.aDisabledDeaneryUser
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.notNullValue
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

class LecturerPreferencesControllerSpec extends BaseIntegrationSpec
        implements CallLecturerPreferencesEndpointAbility, CallUserEndpointAbility {

    def "should respond 200 when registering new account"() {
        given:
            def user = aDisabledDeaneryUser()

        when:
            def response = registerNewUser(user)

        then:
            response.statusCode(OK.value())
                    .body("newUserAssignedId", notNullValue())
                    .body("success", equalTo(true))
    }

    def "should respond 200 when enabling user"() {
        given:
            def userId = 2L

        when:
            def response = enableUser(userId)

        then:
            response.statusCode(OK.value())
    }

    def "should respond 200 when lecturer preferences was successfully updated"() {
        given:
            def request = aNewLecturerPreference()

        when:
            def response = postAuthenticatedToPreferencesUpdate(request)

        then:
            response.statusCode(OK.value())
                    .body("lecturerPreferenceId", notNullValue())
                    .body("userId", notNullValue())
                    .body("startTime", equalTo("08:00"))
                    .body("endTime", equalTo("15:00"))
                    .body("day", equalTo("MONDAY"))
    }

    def "should respond 200 when lecturer preferences has been updated successfully"() {
        given:
            def request = aNewLecturerPreference()
                    .withStartTime("09:00")
                    .withEndTime("14:00")

        when:
            def response = postAuthenticatedToPreferencesUpdate(request)

        then:
            response.statusCode(OK.value())
                    .body("lecturerPreferenceId", notNullValue())
                    .body("userId", notNullValue())
                    .body("startTime", equalTo("09:00"))
                    .body("endTime", equalTo("14:00"))
                    .body("day", equalTo("MONDAY"))
    }

    def "should respond 422 when updating for non existent user"() {
        given:
            def request = aNewLecturerPreference()
                    .withStartTime("09:00")
                    .withEndTime("14:00")
                    .withUserId(3)

        when:
            def response = postAuthenticatedToPreferencesUpdate(request)

        then:
            response.statusCode(UNPROCESSABLE_ENTITY.value())
                    .body("message", equalTo("User No username provided cannot be found."))
    }
}
