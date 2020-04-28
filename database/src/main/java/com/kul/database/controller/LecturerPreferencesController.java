package com.kul.database.controller;

import com.kul.database.exceptions.LecturerPreferenceAlreadyExists;
import com.kul.database.exceptions.NoSuchUserException;
import com.kul.database.model.LecturerPreferenceRequest;
import com.kul.database.service.LecturerPreferencesService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/preferences")
class LecturerPreferencesController {

    private final LecturerPreferencesService lecturerPreferencesService;

    public LecturerPreferencesController(LecturerPreferencesService lecturerPreferencesService) {
        this.lecturerPreferencesService = lecturerPreferencesService;
    }

    @PostMapping(
            value = "/add"
    )
    public void addUserPreferences(@RequestBody LecturerPreferenceRequest lecturerPreferenceRequest) throws LecturerPreferenceAlreadyExists, NoSuchUserException {
        lecturerPreferencesService.addPreferenceForUser(lecturerPreferenceRequest);
    }
}
