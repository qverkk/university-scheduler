package com.kul.database.abilities.dtos

import com.kul.database.abilities.CallLecturerLessonsEndpointAbility
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

import java.util.function.Consumer

import static com.kul.database.abilities.dtos.UserLoginRequests.aUser

@Builder(builderStrategy = SimpleStrategy, prefix = "with")
class LecturesDefinedElementsConfiguration {
    NewLessonTypeRequest lessonType
    NewAreaOfStudyRequest areaOfStudy
    NewLecturerLessonRequest lesson
}

@Builder(builderStrategy = SimpleStrategy, prefix = "with")
class AreaOfStudyConfiguration {
    NewLessonTypeRequest lessonType
    NewAreaOfStudyRequest areaOfStudy
    NewLecturerLessonRequest lesson

    NewLessonTypeRequest normalLesson() {
        lessonType = new NewLessonTypeRequest()
            .withType("Cwiczenia")
    }

    NewLecturerLessonRequest conductedByLecturer(RegisteredUser user) {
        new NewLecturerLessonRequest()
            .withUserId(user.id)
            .withDepartment()
    }
}

trait LessonsManagementAbility implements CallLecturerLessonsEndpointAbility {

    void hasAlreadyDefined(Consumer<LecturesDefinedElementsConfiguration> configurer) {
        LecturesDefinedElementsConfiguration lecturesDefinedElementsConfiguration = new LecturesDefinedElementsConfiguration();
        configurer.accept(lecturesDefinedElementsConfiguration)
        NewAreaOfStudyRequest newAreaOfStudyRequest = lecturesDefinedElementsConfiguration.areaOfStudy

        if (newAreaOfStudyRequest != null) {
            postAuthenticatedUpdateAreaOfStudy(
                    newAreaOfStudyRequest,
                    aUser("admin@admin.com", "admin")
            )
        }
        if (lecturesDefinedElementsConfiguration.lessonType != null) {
            postAuthenticatedAddLessonType(
                    lecturesDefinedElementsConfiguration.lessonType,
                    aUser("admin@admin.com", "admin")
            )
        }
        if (lecturesDefinedElementsConfiguration.lesson != null) {
            postAuthenticatedLecturerLessonForUser(
                    lecturesDefinedElementsConfiguration.lesson,
                    aUser("admin@admin.com", "admin")
            )
        }
    }

    NewAreaOfStudyRequest mathematics(Consumer<AreaOfStudyConfiguration> configuration) {
        AreaOfStudyConfiguration areaOfStudyConfiguration = new AreaOfStudyConfiguration();
        configuration.accept(areaOfStudyConfiguration)

        return new NewAreaOfStudyRequest()
            .withArea("Mathematics")
            .withDepartment("Jakis tam wydzial")
    }
}
