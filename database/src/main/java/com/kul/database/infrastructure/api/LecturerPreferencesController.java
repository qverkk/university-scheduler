package com.kul.database.infrastructure.api;

import com.kul.database.lecturerpreferences.api.*;
import com.kul.database.lecturerpreferences.domain.LecturerPreferences;
import com.kul.database.lecturerpreferences.domain.LecturerPreferencesService;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;

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
    public AddLecturePreferenceResponse addLecturerPreferences(@RequestBody AddLecturerPreferenceRequest lecturerPreferenceRequest) throws Exception {
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
    public UpdateLecturerPreferenceResponse updateLecturerPreference(@RequestBody UpdateLecturerPreferenceRequest request) throws Exception {
        final LecturerPreferences lecturerPreferences = lecturerPreferencesService.updatePreferenceForUser(request);
        return new UpdateLecturerPreferenceResponse(
                lecturerPreferences.getId(),
                request.getUserId(),
                lecturerPreferences.getStartTime(),
                lecturerPreferences.getEndTime(),
                lecturerPreferences.getDay()
        );
    }

    @GetMapping(
            value = "/fetch/{userId}/{day}"
    )
    public FetchLecturerPreferenceResponse fetchLecturerPreference(@PathVariable Long userId, @PathVariable DayOfWeek day) throws Exception {
        LecturerPreferences lecturerPreferences = lecturerPreferencesService.fetchPreferenceForUserAndDay(userId, day);
        return new FetchLecturerPreferenceResponse(
                lecturerPreferences.getStartTime(),
                lecturerPreferences.getEndTime()
        );
    }
}
