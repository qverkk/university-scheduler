package com.kul.database.service;

import com.kul.database.exceptions.LecturerPreferenceAlreadyExists;
import com.kul.database.exceptions.LecturerPreferenceDoesntExist;
import com.kul.database.exceptions.NoSuchUserException;
import com.kul.database.model.AddLecturerPreferenceRequest;
import com.kul.database.model.LecturerPreferences;
import com.kul.database.model.UpdateLecturerPreferenceRequest;

public interface LecturerPreferencesService {
    LecturerPreferences addPreferenceForUser(AddLecturerPreferenceRequest lecturerPreferenceRequest) throws NoSuchUserException, LecturerPreferenceAlreadyExists;

    LecturerPreferences updatePreferenceForUser(UpdateLecturerPreferenceRequest request) throws NoSuchUserException, LecturerPreferenceDoesntExist;
}
