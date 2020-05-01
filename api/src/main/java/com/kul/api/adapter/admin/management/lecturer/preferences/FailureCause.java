package com.kul.api.adapter.admin.management.lecturer.preferences;

import java.util.stream.Stream;

public enum FailureCause {
    NoSuchUserProvided,
    LecturerPreferenceDoesntExist,
    InsufficientPermissionsToUpdateLecturerPreferences,
    LecturerPreferenceAlreadyExists,
    MethodArgumentNotValidException,
    Unknown;

    public static FailureCause findByCode(String code) {
        return Stream.of(values())
                .filter(value -> value.name().equals(code))
                .findAny()
                .orElse(Unknown);
    }
}
