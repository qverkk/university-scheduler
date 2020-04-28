package com.kul.database.service;

import com.kul.database.exceptions.LecturerPreferenceAlreadyExists;
import com.kul.database.exceptions.NoSuchUserException;
import com.kul.database.model.AddLecturerPreferenceRequest;
import com.kul.database.model.LecturerPreferences;

public interface LecturerPreferencesService {
    LecturerPreferences addPreferenceForUser(AddLecturerPreferenceRequest lecturerPreferenceRequest) throws NoSuchUserException, LecturerPreferenceAlreadyExists;
}
