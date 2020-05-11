package com.kul.database.abilities.dtos

import com.kul.database.abilities.CallLecturerLessonsEndpointAbility
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

import java.util.function.Supplier

import static com.kul.database.abilities.dtos.UserLoginRequests.aUser

@Builder(builderStrategy = SimpleStrategy, prefix = "with")
class LecturesDefinedElementsConfiguration {
    NewLessonTypeRequest lessonType
    NewAreaOfStudyRequest areaOfStudy
    NewLecturerLessonRequest lesson
}

trait LessonsManagementAbility implements CallLecturerLessonsEndpointAbility {

    void hasAlreadyDefined(Supplier<LecturesDefinedElementsConfiguration> configurer) {
        LecturesDefinedElementsConfiguration lecturesDefinedElementsConfiguration = configurer.get();

        if (lecturesDefinedElementsConfiguration.areaOfStudy != null) {
            postAuthenticatedUpdateAreaOfStudy(
                    lecturesDefinedElementsConfiguration.areaOfStudy,
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
}
