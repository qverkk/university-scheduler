package com.kul.database.lecturerpreferences.adapter;

import com.kul.database.lecturerpreferences.domain.LecturerPreferences;

public class LecturerPreferencesEntityMapper {
    public static LecturerPreferences toDomain(LecturerPreferencesEntity entity) {
        return new LecturerPreferences(
                entity.getId(),
                entity.getUser(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getDay(),
                entity.getLastUsedUsername(),
                entity.getVersion()
        );
    }
}
