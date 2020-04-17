package com.kul.database

import com.kul.database.abilities.CallAuthEndpointAbility
import spock.lang.Stepwise

import static com.kul.database.abilities.dtos.NewUserRequests.aDisabledDeaneryUser
import static com.kul.database.abilities.dtos.UserLoginRequests.aUser
import static org.hamcrest.Matchers.contains
import static org.hamcrest.Matchers.containsString
import static org.hamcrest.Matchers.equalTo
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNAUTHORIZED
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

@Stepwise
class UserControllerSpec extends BaseIntegrationSpec implements CallAuthEndpointAbility {
    def 'should respond 442 when regular user passes unprocessable email'() {
        given:
            def user = aDisabledDeaneryUser()
                    .withUsername("janusz.kowalski")
                    .withPassword("admin")

        when:
            def response = registerNewUser(user)

        then:
            response.statusCode(UNPROCESSABLE_ENTITY.value())
                    .body(containsString("\"message\":\"Email should be valid\""))
            response.extract()
    }

    def 'should respond 404 when user does not exist'() {
        given:
            def request = aUser("janusz@nosacz.pl", "Januszu123!@#a")

        when:
            def response = authenticateUser(request)

        then:
            response.statusCode(NOT_FOUND.value())
    }

    def 'should respond 200 when regular user is registered'() {
        given:
            def user = aDisabledDeaneryUser()

        when:
            def response = registerNewUser(user)

        then:
            response.statusCode(OK.value())
                    .body(equalTo("true"))
            response.extract()
    }

    def 'should respond 401 when regular user has disabled token and trying to authenticate'() {
        given:
            def request = aUser("janusz@nosacz.pl", "Januszu123!@#a")

        when:
            def response = authenticateUser(request)

        then:
            response.statusCode(NOT_ACCEPTABLE.value())
    }

    // etc.
}
