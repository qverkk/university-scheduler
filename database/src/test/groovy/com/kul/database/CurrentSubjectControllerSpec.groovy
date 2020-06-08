package com.kul.database

import com.kul.database.abilities.CallClassroomEndpointAbility
import com.kul.database.abilities.CallCurrentSubjectsEndpointAbility
import com.kul.database.abilities.dtos.AddedClassroom
import com.kul.database.abilities.dtos.ClassroomManagementAbility
import com.kul.database.abilities.dtos.RegisteredUser
import com.kul.database.abilities.dtos.UserLoginRequest
import com.kul.database.abilities.dtos.UsersManagementAbility

import static com.kul.database.abilities.dtos.ClassroomFixtures.onlyWykladowaType
import static com.kul.database.abilities.dtos.ClassroomFixtures.uniqueClassroom
import static com.kul.database.abilities.dtos.NewCurrentSubjectRequests.aNewCurrentSubject
import static com.kul.database.abilities.dtos.UsersFixtures.janKowalski
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.notNullValue
import static org.hamcrest.Matchers.notNullValue
import static org.springframework.http.HttpStatus.OK

class CurrentSubjectControllerSpec extends BaseIntegrationSpec
    implements CallClassroomEndpointAbility, UsersManagementAbility, ClassroomManagementAbility, CallCurrentSubjectsEndpointAbility {


    // TODO: Finish this test...
    def "should return 200 when adding a class"() {
        given:
            RegisteredUser user = hasRegisteredUser {
                janKowalski()
                        .whoIsEnabled()
            }
            AddedClassroom classroom = hasAddedClassroom {
                uniqueClassroom()
            }
            println "Classroom id: " + classroom.classroomId
        when:
            def request = postAuthenticatedAddCurrentSubjectForUser(
                    aNewCurrentSubject(),
                    new UserLoginRequest()
                            .withUsername(user.username)
                            .withPassword(user.password)
            )
        then:
            println "Req: " + request
            request.statusCode(OK.value())
    }

    def "should return 422 when adding a class without a classroom"() {

    }
}
