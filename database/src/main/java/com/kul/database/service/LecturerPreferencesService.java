package com.kul.database.service;

import com.kul.database.exceptions.LecturerPreferenceAlreadyExists;
import com.kul.database.exceptions.NoSuchUserException;
import com.kul.database.model.LecturerPreferenceRequest;

public interface LecturerPreferencesService {
    void addPreferenceForUser(LecturerPreferenceRequest lecturerPreferenceRequest) throws NoSuchUserException, LecturerPreferenceAlreadyExists;
}
