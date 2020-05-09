package com.kul.database

import com.kul.database.abilities.CallLecturerLessonsEndpointAbility
import com.kul.database.abilities.dtos.RegisteredUser
import com.kul.database.abilities.dtos.UsersManagementAbility
import com.kul.database.lecturerlessons.domain.areaofstudy.AreaOfStudy
import com.kul.database.usermanagement.domain.AuthorityEnum

import static com.kul.database.abilities.dtos.NewLecturerLessonsRequests.aNewLecturerLesson
import static com.kul.database.abilities.dtos.UsersFixtures.janKowalski
import static com.kul.database.abilities.dtos.UserLoginRequests.aUser
import static org.springframework.http.HttpStatus.FORBIDDEN
import static org.springframework.http.HttpStatus.OK
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.notNullValue

class LecturerLessonsControllerSpec extends BaseIntegrationSpec
    implements CallLecturerLessonsEndpointAbility, UsersManagementAbility {

    def "should respond 200 when adding new lesson for prowadzacy"() {
        given:
            def kowalski = janKowalski()
            RegisteredUser user = hasRegisteredUser {
                    kowalski.whoIsEnabled()
            }
            def lesson = aNewLecturerLesson()
                    .withUserId(user.id)

        when:
            def response = postAuthenticatedLecturerLessonForUser(lesson, aUser(kowalski.username(), kowalski.password()))

        then:
            response.statusCode(OK.value())
    }

    def "should respond 403 when adding new lesson by dziekanat"() {
        given:
            def kowalski = janKowalski()
                    .whoHasAuthority(AuthorityEnum.DZIEKANAT)
            RegisteredUser user = hasRegisteredUser  {
                kowalski.whoIsEnabled()
            }
            def lesson = aNewLecturerLesson()
                .withUserId(user.id)
        when:
            def response = postAuthenticatedLecturerLessonForUser(lesson, aUser(kowalski.username(), kowalski.password()))

        then:
            response.statusCode(FORBIDDEN.value())
                .body("code", equalTo("UserCannotHaveLessons"))
                .body("message", notNullValue())
    }

    def "should respond 200 when updating a lesson for prowadzacy"() {
        given:
            def kowalski = janKowalski()
            RegisteredUser user = hasRegisteredUser {
                kowalski.whoIsEnabled()
            }
            def lesson = aNewLecturerLesson()
                    .withUserId(user.id)
            def updateLesson = aNewLecturerLesson()
                    .withUserId(user.id)
                    .withAreaOfStudy(AreaOfStudy.MATEMATYKA)

        when:
            def response = postAuthenticatedLecturerLessonForUser(lesson, aUser(kowalski.username(), kowalski.password()))
            def updateResponse = postAuthenticatedLecturerLessonForUser(updateLesson, aUser(kowalski.username(), kowalski.password()))

        then:
            response.statusCode(OK.value())
            updateResponse.statusCode(OK.value())
                .body("areaOfStudy", equalTo(AreaOfStudy.MATEMATYKA.name()))
    }
}
