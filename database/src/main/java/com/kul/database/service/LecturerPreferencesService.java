package com.kul.database.service;

import com.kul.database.model.AddLecturerPreferenceRequest;
import com.kul.database.model.LecturerPreferences;
import com.kul.database.model.UpdateLecturerPreferenceRequest;

import java.time.DayOfWeek;

public interface LecturerPreferencesService {
    LecturerPreferences addPreferenceForUser(AddLecturerPreferenceRequest lecturerPreferenceRequest) throws Exception;

    LecturerPreferences updatePreferenceForUser(UpdateLecturerPreferenceRequest request) throws Exception;

    LecturerPreferences fetchPreferenceForUserAndDay(Long userId, DayOfWeek day) throws Exception;
}
