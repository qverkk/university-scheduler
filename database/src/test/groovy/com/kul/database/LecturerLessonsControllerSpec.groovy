package com.kul.database

import com.kul.database.abilities.CallLecturerLessonsEndpointAbility
import com.kul.database.abilities.dtos.LecturesDefinedElementsConfiguration
import com.kul.database.abilities.dtos.LessonsManagementAbility
import com.kul.database.abilities.dtos.RegisteredUser
import com.kul.database.abilities.dtos.UsersManagementAbility
import com.kul.database.usermanagement.domain.AuthorityEnum

import static com.kul.database.abilities.dtos.NewLecturerLessonsRequests.aNewLecturerLesson
import static com.kul.database.abilities.dtos.UsersFixtures.janKowalski
import static com.kul.database.abilities.dtos.UserLoginRequests.aUser
import static com.kul.database.abilities.dtos.NewLessonTypeRequests.aNewLessonType
import static com.kul.database.abilities.dtos.NewAreaOfStudyRequests.aNewAreaOfStudy
import static org.springframework.http.HttpStatus.FORBIDDEN
import static org.springframework.http.HttpStatus.OK
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.notNullValue
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

class LecturerLessonsControllerSpec extends BaseIntegrationSpec
    implements CallLecturerLessonsEndpointAbility, UsersManagementAbility, LessonsManagementAbility {

    def "should respond 422 when trying to add a new lesson for prowadzacy"() {
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
            response.statusCode(UNPROCESSABLE_ENTITY.value())
    }

    def "should respond 200 when adding a new lesson type"() {
        given:
            def type = aNewLessonType()
            def kowalski = janKowalski()
            RegisteredUser user = hasRegisteredUser {
                kowalski.whoIsEnabled()
            }
        when:
            def response = postAuthenticatedAddLessonType(type, aUser(kowalski.username(), kowalski.password()))

        then:
            response.statusCode(OK.value())
    }

    def "should respond 200 when adding a new area of study"() {
        given:
            def areaOfstudy = aNewAreaOfStudy()
            def kowalski = janKowalski()
            RegisteredUser user = hasRegisteredUser {
                kowalski.whoIsEnabled()
            }
        when:
            def response = postAuthenticatedUpdateAreaOfStudy(areaOfstudy, aUser(kowalski.username(), kowalski.password()))

        then:
            response.statusCode(OK.value())
                .body("area", equalTo("Informatyka"))
                .body("department", equalTo("Jakis tam wydzial"))
                .body("id", notNullValue())
    }

    def "should respond 200 when adding new lesson for prowadzacy"() {
        given:
            def lessonType = aNewLessonType()
            def areaOfstudy = aNewAreaOfStudy()
            def kowalski = janKowalski()
            RegisteredUser user = hasRegisteredUser {
                    kowalski.whoIsEnabled()
            }
            hasAlreadyDefined {
                new LecturesDefinedElementsConfiguration()
                    .withAreaOfStudy(areaOfstudy)
                    .withLessonType(lessonType)
            }
            def lesson = aNewLecturerLesson()
                    .withUserId(user.id)
                    .withDepartment(areaOfstudy.department)
                    .withArea(areaOfstudy.area)
                    .withLessonType(lessonType.type)

        when:
            def addLessonResponse = postAuthenticatedLecturerLessonForUser(lesson, aUser(kowalski.username(), kowalski.password()))
        then:
            addLessonResponse.statusCode(OK.value())
    }

    def "should respond 403 when adding new lesson by dziekanat"() {
        given:
            def lessonType = aNewLessonType()
            def areaOfstudy = aNewAreaOfStudy()
            def kowalski = janKowalski()
                    .whoHasAuthority(AuthorityEnum.DZIEKANAT)
            RegisteredUser user = hasRegisteredUser  {
                kowalski.whoIsEnabled()
            }
            hasAlreadyDefined {
                new LecturesDefinedElementsConfiguration()
                        .withAreaOfStudy(areaOfstudy)
                        .withLessonType(lessonType)
            }
            def lesson = aNewLecturerLesson()
                    .withUserId(user.id)
                    .withDepartment(areaOfstudy.department)
                    .withArea(areaOfstudy.area)
                    .withLessonType(lessonType.type)
        when:
            def response = postAuthenticatedLecturerLessonForUser(lesson, aUser(kowalski.username(), kowalski.password()))

        then:
            response.statusCode(FORBIDDEN.value())
                .body("code", equalTo("UserCannotHaveLessons"))
                .body("message", notNullValue())
    }

    def "should respond 200 when updating a lesson for prowadzacy"() {
        given:
            def lessonType = aNewLessonType()
            def areaOfstudy = aNewAreaOfStudy()
            def updatedAreaOfStudy = aNewAreaOfStudy()
                .withArea("Matematyka")

            def kowalski = janKowalski()
            RegisteredUser user = hasRegisteredUser {
                kowalski.whoIsEnabled()
            }

            def lesson = aNewLecturerLesson()
                    .withUserId(user.id)
                    .withDepartment(areaOfstudy.department)
                    .withArea(areaOfstudy.area)
                    .withLessonType(lessonType.type)

            hasAlreadyDefined {
                new LecturesDefinedElementsConfiguration()
                        .withAreaOfStudy(areaOfstudy)
                        .withLessonType(lessonType)
                        .withLesson(lesson)
            }

            hasAlreadyDefined {
                new LecturesDefinedElementsConfiguration()
                    .withAreaOfStudy(updatedAreaOfStudy)
            }

            def updateLesson = aNewLecturerLesson()
                    .withUserId(user.id)
                    .withDepartment(updatedAreaOfStudy.department)
                    .withArea(updatedAreaOfStudy.area)
                    .withLessonType(lessonType.type)

        when:
            def updateResponse = postAuthenticatedLecturerLessonForUser(updateLesson, aUser(kowalski.username(), kowalski.password()))

        then:
            updateResponse.statusCode(OK.value())
                .body("areaOfStudy.area", equalTo("Matematyka"))
    }
}
