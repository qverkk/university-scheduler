package com.kul.database.lecturerpreferences.api;

import com.kul.database.lecturerpreferences.api.model.*;
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

    @PutMapping(
            value = "/update"
    )
    public UpdateLecturerPreferenceResponse updateLecturerPreference(@RequestBody UpdateLecturerPreferenceRequest request) {
        final LecturerPreferences lecturerPreferences = lecturerPreferencesService.updatePreferenceForUser(
                UpdateLecturerPreferenceMapper.toDomain(request)
        );
        return UpdateLecturerPreferenceMapper.fromDomain(lecturerPreferences);
    }

    @GetMapping(
            value = "/fetch/{userId}/{day}"
    )
    public FetchLecturerPreferenceResponse fetchLecturerPreference(@PathVariable Long userId, @PathVariable DayOfWeek day) {
        LecturerPreferences lecturerPreferences = lecturerPreferencesService.fetchPreferenceForUserAndDay(userId, day);
        return FetchLecturerPreferenceMapper.toResponse(lecturerPreferences);
    }
}
