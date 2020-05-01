package com.kul.database.lecturerpreferences.domain;

import com.kul.database.lecturerpreferences.domain.exceptions.InsufficientPermissionsToUpdateLecturerPreferences;
import com.kul.database.lecturerpreferences.domain.exceptions.LecturerPreferenceDoesntExist;
import com.kul.database.usermanagement.domain.User;
import com.kul.database.usermanagement.domain.UserRepository;
import com.kul.database.usermanagement.domain.exceptions.NoSuchUserException;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;

@Service
public class LecturerPreferencesService {

    private final LecturerPreferencesRepository lecturerPreferencesRepository;
    private final UserRepository userRepository;

    public LecturerPreferencesService(LecturerPreferencesRepository lecturerPreferencesRepository, UserRepository userRepository) {
        this.lecturerPreferencesRepository = lecturerPreferencesRepository;
        this.userRepository = userRepository;
    }

    public LecturerPreferences updatePreferenceForUser(UpdateLecturerPreference request) throws RuntimeException {
        final User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NoSuchUserException("No username provided"));

        final LecturerPreferences preference = lecturerPreferencesRepository.findByDayAndUser(request.getDay(), user)
                .orElse(new LecturerPreferences(user, request.getStartTime(), request.getEndTime(), request.getDay()));

        if (!preference.canBeUpdatedBy(user)) {
            throw new InsufficientPermissionsToUpdateLecturerPreferences(
                    "Only admin, dziekanat or user for this permission can update them"
            );
        }

        preference.changeScheduleWindow(request.getStartTime(), request.getEndTime(), user);

        return lecturerPreferencesRepository.save(preference);
    }

    public LecturerPreferences fetchPreferenceForUserAndDay(Long userId, DayOfWeek day) throws RuntimeException {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchUserException("No username provided"));

        return lecturerPreferencesRepository.findByDayAndUser(day, user)
                .orElseThrow(() -> new LecturerPreferenceDoesntExist(
                        String.format("Preference for %s %s doesn't exists", user.getUsername(), day)
                ));
    }
}
