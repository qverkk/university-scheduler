package com.kul.database

import com.kul.database.abilities.CallLecturerPreferencesEndpointAbility
import com.kul.database.abilities.CallUserEndpointAbility
import com.kul.database.abilities.dtos.RegisteredUser
import com.kul.database.abilities.dtos.UsersManagementAbility

import static com.kul.database.abilities.dtos.NewLecturerPreferencesRequests.aNewLecturerPreference
import static com.kul.database.abilities.dtos.UsersFixtures.janKowalski
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.notNullValue
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

class LecturerPreferencesControllerSpec extends BaseIntegrationSpec
        implements CallLecturerPreferencesEndpointAbility, CallUserEndpointAbility, UsersManagementAbility {

    def "should respond 200 when enabling user"() {
        given:
            RegisteredUser user = hasRegisteredUser {
                janKowalski().whoIsDisabled()
            }

        when:
            def response = enableUser(user.id)

        then:
            response.statusCode(OK.value())
    }

    def "should respond 200 when lecturer preferences was successfully updated"() {
        given:
            RegisteredUser user = hasRegisteredUser {
                janKowalski().whoIsDisabled()
            }
            def request = aNewLecturerPreference()
                    .withUserId(user.id)

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
            RegisteredUser user = hasRegisteredUser {
                janKowalski().whoIsEnabled()
            }
            def addPreferenceRequest = aNewLecturerPreference()
                    .withUserId(user.id)

            def updatePreferenceRequest = aNewLecturerPreference()
                    .withUserId(user.id)
                    .withStartTime("09:00")
                    .withEndTime("14:00")

        when:
            def addResponse = postAuthenticatedToPreferencesUpdate(addPreferenceRequest)
            def updateResponse = postAuthenticatedToPreferencesUpdate(updatePreferenceRequest)

        then:
            addResponse.statusCode(OK.value())

            updateResponse.statusCode(OK.value())
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
                    .withUserId(999999)

        when:
            def response = postAuthenticatedToPreferencesUpdate(request)

        then:
            response.statusCode(UNPROCESSABLE_ENTITY.value())
                    .body("message", equalTo("User No username provided cannot be found."))
    }
}
