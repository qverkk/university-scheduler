package com.kul.database.abilities.dtos

import com.kul.database.abilities.CallClassroomEndpointAbility
import groovy.transform.builder.Builder
import io.restassured.path.json.JsonPath
import org.junit.After

import java.util.function.Supplier

@Builder(prefix = "with")
class AddedClassrooms {
    Long id
    String name
}

@Builder(prefix = "with")
class AddedClassroomTypes {
    Long id
    String name
}

@Builder(prefix = "with")
class AddedClassroom {
    Long classroomId
    String classroomName
    Integer classroomSize
    List<AddedClassroomTypes> types
}

class ClassroomFixtures {
    static ClassroomConfiguration uniqueClassroom() {
        return new ClassroomConfiguration()
            .whichHasClassroomName("B21")
            .whichHasClassroomSize(70)
            .whichHasAdditionalType("Wykladowa")
    }

    static ClassroomConfiguration onlyWykladowaType() {
        return new ClassroomConfiguration()
            .whichHasAdditionalType("Wykladowa")
    }
}

class ClassroomConfiguration {
    String classroomName
    Integer classroomSize
    List<String> classroomTypes = new LinkedList<>()

    ClassroomConfiguration whichHasClassroomName(String classroomName) {
        this.classroomName = classroomName
        this
    }

    ClassroomConfiguration whichHasClassroomSize(Integer classroomSize) {
        this.classroomSize = classroomSize
        this
    }

    ClassroomConfiguration whichHasAdditionalType(String type) {
        this.classroomTypes.add(type)
        this
    }

    ClassroomConfiguration whichHasTypes(List<String> types) {
        this.classroomTypes = types
        this
    }

    List<NewClassroomTypeRequest> toNewClassroomTypesRequest() {
        List<NewClassroomTypeRequest> result = new LinkedList<>()
        classroomTypes.forEach {
            result.add(new NewClassroomTypeRequest()
                    .withName(it))
        }
        return result
    }

    NewClassroomRequest toNewClassroomRequest() {
        return new NewClassroomRequest()
                .withName(classroomName)
                .withClassroomSize(classroomSize)
                .withClassroomTypes(classroomTypes)
    }
}

trait ClassroomManagementAbility implements CallClassroomEndpointAbility {
    private final List<AddedClassrooms> addedClassrooms = new LinkedList<>()
    private final List<AddedClassroomTypes> addedClassroomTypes = new LinkedList<>()

    AddedClassroom hasAddedClassroom(Supplier<ClassroomConfiguration> configurer) {
        ClassroomConfiguration classroomConfiguration = configurer.get()

        classroomConfiguration.toNewClassroomTypesRequest().forEach {
            addNewClassroomType(it)
        }

        if (classroomConfiguration.classroomSize != null) {
            def request = postAuthenticatedClassroom(classroomConfiguration.toNewClassroomRequest(), adminLogin())
            def jsonPath = request.extract().body().jsonPath()
            addedClassrooms.add(
                    AddedClassrooms.builder()
                        .withName(jsonPath.get("name"))
                        .withId(Integer.parseInt(jsonPath.get("id").toString()))
                    .build()
            )
            return AddedClassroom.builder()
                    .withClassroomName(classroomConfiguration.classroomName)
                    .withClassroomSize(classroomConfiguration.classroomSize)
                    .withTypes(addedClassroomTypes)
                    .withClassroomId(Integer.parseInt(jsonPath.get("id").toString()))
                    .build()
        }

        return AddedClassroom.builder()
                .withTypes(addedClassroomTypes)
                .build()
    }

    private addNewClassroomType(NewClassroomTypeRequest type) {
        def request = postAuthenticatedClassroomType(type, adminLogin())
        def jsonPath = request.extract().body().jsonPath()
        println request.extract().body()
        addedClassroomTypes.add(
                AddedClassroomTypes.builder()
                    .withId(Integer.parseInt(jsonPath.get("id").toString()))
                    .withName(jsonPath.get("name"))
                .build()
        )
    }

    private UserLoginRequest adminLogin() {
        return new UserLoginRequest()
                .withUsername("admin@admin.com")
                .withPassword("admin")
    }

    @After
    void deleteAllEntities() {
        addedClassrooms.forEach {
            postAuthenticatedDeleteClassroom(it.id)
        }
        addedClassroomTypes.forEach {
            postAuthenticatedDeleteClassroomType(it.id)
        }
    }
}