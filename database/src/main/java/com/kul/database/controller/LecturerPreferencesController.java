package com.kul.database.controller;

import com.kul.database.exceptions.LecturerPreferenceAlreadyExists;
import com.kul.database.exceptions.NoSuchUserException;
import com.kul.database.model.AddLecturePreferenceResponse;
import com.kul.database.model.AddLecturerPreferenceRequest;
import com.kul.database.model.LecturerPreferences;
import com.kul.database.service.LecturerPreferencesService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

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
    public AddLecturePreferenceResponse addUserPreferences(@RequestBody AddLecturerPreferenceRequest lecturerPreferenceRequest) throws LecturerPreferenceAlreadyExists, NoSuchUserException {
        final LecturerPreferences lecturerPreferences = lecturerPreferencesService.addPreferenceForUser(lecturerPreferenceRequest);
        return new AddLecturePreferenceResponse(
                lecturerPreferences.getId(),
                lecturerPreferenceRequest.getUserId(),
                lecturerPreferences.getStartTime(),
                lecturerPreferences.getEndTime(),
                lecturerPreferences.getDay()
        );
    }
}
