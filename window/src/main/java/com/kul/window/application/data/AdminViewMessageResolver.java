package com.kul.window.application.data;

interface AdminViewMessageResolver {
    String nonExistingLecturer();
    String nonExistingPreference();
    String insufficientLecturerPreferences();
    String alreadyExistingPreference();
    String nonValidPreferences();
    String success();
    String nonValidTime();
    String classroomTypeAlreadyExists();
    String entityHasChildren();
}
