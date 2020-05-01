package com.kul.database.infrastructure.api;

import com.kul.database.lecturerpreferences.api.FetchLecturerPreferenceResponse;
import com.kul.database.lecturerpreferences.api.UpdateLecturerPreferenceResponse;
import com.kul.database.lecturerpreferences.domain.LecturerPreferences;
import com.kul.database.lecturerpreferences.domain.LecturerPreferencesService;
import com.kul.database.lecturerpreferences.domain.UpdateLecturerPreference;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;

@RestController
@RequestMapping(value = "/preferences")
class LecturerPreferencesController {

    private final LecturerPreferencesService lecturerPreferencesService;

    public LecturerPreferencesController(LecturerPreferencesService lecturerPreferencesService) {
        this.lecturerPreferencesService = lecturerPreferencesService;
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/update"
    )
    public UpdateLecturerPreferenceResponse updateLecturerPreference(@RequestBody UpdateLecturerPreference request) throws RuntimeException {
        final LecturerPreferences lecturerPreferences = lecturerPreferencesService.updatePreferenceForUser(request);
        return UpdateLecturerPreferenceResponse.fromDomain(lecturerPreferences);
    }

    @GetMapping(
            value = "/fetch/{userId}/{day}"
    )
    public FetchLecturerPreferenceResponse fetchLecturerPreference(@PathVariable Long userId, @PathVariable DayOfWeek day) throws RuntimeException {
        LecturerPreferences lecturerPreferences = lecturerPreferencesService.fetchPreferenceForUserAndDay(userId, day);
        return new FetchLecturerPreferenceResponse(
                lecturerPreferences.getStartTime(),
                lecturerPreferences.getEndTime()
        );
    }
}
