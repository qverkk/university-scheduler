package com.kul.database.controller;

import com.kul.database.exceptions.LecturerPreferenceAlreadyExists;
import com.kul.database.exceptions.LecturerPreferenceDoesntExist;
import com.kul.database.exceptions.NoSuchUserException;
import com.kul.database.model.*;
import com.kul.database.service.LecturerPreferencesService;
import org.springframework.web.bind.annotation.*;

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
    public AddLecturePreferenceResponse addLecturerPreferences(@RequestBody AddLecturerPreferenceRequest lecturerPreferenceRequest) throws LecturerPreferenceAlreadyExists, NoSuchUserException {
        final LecturerPreferences lecturerPreferences = lecturerPreferencesService.addPreferenceForUser(lecturerPreferenceRequest);
        return new AddLecturePreferenceResponse(
                lecturerPreferences.getId(),
                lecturerPreferenceRequest.getUserId(),
                lecturerPreferences.getStartTime(),
                lecturerPreferences.getEndTime(),
                lecturerPreferences.getDay()
        );
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/update"
    )
    public UpdateLecturerPreferenceResponse updateLecturerPreference(@RequestBody UpdateLecturerPreferenceRequest request) throws NoSuchUserException, LecturerPreferenceDoesntExist {
        final LecturerPreferences lecturerPreferences = lecturerPreferencesService.updatePreferenceForUser(request);
        return new UpdateLecturerPreferenceResponse(
                lecturerPreferences.getId(),
                request.getUserId(),
                lecturerPreferences.getStartTime(),
                lecturerPreferences.getEndTime(),
                lecturerPreferences.getDay()
        );
    }
}
