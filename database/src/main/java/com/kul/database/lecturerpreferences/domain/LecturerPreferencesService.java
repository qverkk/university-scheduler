package com.kul.database.lecturerpreferences.domain;

import com.kul.database.lecturerpreferences.api.AddLecturerPreferenceRequest;
import com.kul.database.lecturerpreferences.api.UpdateLecturerPreferenceRequest;

import java.time.DayOfWeek;

public interface LecturerPreferencesService {
    LecturerPreferences addPreferenceForUser(AddLecturerPreferenceRequest lecturerPreferenceRequest) throws Exception;

    LecturerPreferences updatePreferenceForUser(UpdateLecturerPreferenceRequest request) throws Exception;

    LecturerPreferences fetchPreferenceForUserAndDay(Long userId, DayOfWeek day) throws Exception;
}
