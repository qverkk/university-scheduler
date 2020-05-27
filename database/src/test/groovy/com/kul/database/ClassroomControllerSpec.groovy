package com.kul.database

import com.kul.database.abilities.dtos.AddedClassroom
import com.kul.database.abilities.CallClassroomEndpointAbility
import com.kul.database.abilities.dtos.ClassroomConfiguration
import com.kul.database.abilities.dtos.ClassroomManagementAbility
import com.kul.database.abilities.dtos.RegisteredUser
import com.kul.database.abilities.dtos.UserLoginRequest
import com.kul.database.abilities.dtos.UsersManagementAbility

import static com.kul.database.abilities.dtos.ClassroomFixtures.onlyWykladowaType
import static com.kul.database.abilities.dtos.ClassroomFixtures.uniqueClassroom
import static com.kul.database.abilities.dtos.UsersFixtures.janKowalski
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.notNullValue
import static org.hamcrest.Matchers.typeCompatibleWith
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

class ClassroomControllerSpec extends BaseIntegrationSpec
        implements CallClassroomEndpointAbility, UsersManagementAbility, ClassroomManagementAbility {

    def "should respond 422 when trying to add a class without an existing type in the database"() {
            given:
            RegisteredUser user = hasRegisteredUser {
                janKowalski()
                        .whoIsEnabled()
            }
            def classroom = uniqueClassroom()

        when:
            def request = postAuthenticatedClassroom(
                    classroom.toNewClassroomRequest(),
                    new UserLoginRequest()
                            .withUsername(user.username)
                            .withPassword(user.password)
        )
        then:
            request.statusCode(UNPROCESSABLE_ENTITY.value())
                    .body("message", equalTo("There are no types in the database. Please add some"))
                    .body("code", equalTo("NoClassroomTypes"))
    }

    def "should respond 422 when adding the same type"() {
        given:
            RegisteredUser user = hasRegisteredUser {
                janKowalski()
                        .whoIsEnabled()
            }
            def type = onlyWykladowaType()
            AddedClassroom classroom = hasAddedClassroom {
                type
            }
        when:
            def request = postAuthenticatedClassroomType(
                    type.toNewClassroomTypesRequest().get(0),
                    new UserLoginRequest()
                            .withUsername(user.username)
                            .withPassword(user.password)
        )
        then:
            request.statusCode(UNPROCESSABLE_ENTITY.value())
                    .body("message", equalTo("Classroom type for " + type.getClassroomTypes().get(0) + " already exists"))
                    .body("code", equalTo("ClassroomTypeAlreadyExists"))
    }

    def "should respond 200 when trying to add a class"() {
        given:
            RegisteredUser user = hasRegisteredUser {
                janKowalski()
                        .whoIsEnabled()
            }
            def newClassroom = uniqueClassroom()
            AddedClassroom classroom = hasAddedClassroom {
                onlyWykladowaType()
            }
        when:
            def request = postAuthenticatedClassroom(
                    newClassroom.toNewClassroomRequest(),
                    new UserLoginRequest()
                            .withUsername(user.username)
                            .withPassword(user.password)
            )
        then:
            request.statusCode(OK.value())
                .body("id", notNullValue())
                .body("classroomTypes", notNullValue())
                .body("classroomSize", equalTo(newClassroom.classroomSize))
    }
}
