package com.kul.database.lecturerpreferences.domain;

import com.kul.database.lecturerpreferences.domain.exceptions.InsufficientPermissionsToUpdateLecturerPreferences;
import com.kul.database.lecturerpreferences.domain.exceptions.LecturerPreferenceDoesntExist;
import com.kul.database.usermanagement.domain.User;
import com.kul.database.usermanagement.domain.UserRepository;
import com.kul.database.usermanagement.domain.exceptions.NoSuchUserException;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.Optional;

@Service("Lecturer preferences service")
public class LecturerPreferencesService {

    private final LecturerPreferencesRepository lecturerPreferencesRepository;
    private final UserRepository userRepository;

    public LecturerPreferencesService(LecturerPreferencesRepository lecturerPreferencesRepository, UserRepository userRepository) {
        this.lecturerPreferencesRepository = lecturerPreferencesRepository;
        this.userRepository = userRepository;
    }

    public LecturerPreferences updatePreferenceForUser(UpdateLecturerPreference request) throws RuntimeException {
        final Optional<User> optionalUser = userRepository.findById(request.getUserId());
        if (!optionalUser.isPresent()) {
            throw new NoSuchUserException("No username provided");
        }
        final User user = optionalUser.get();
        if (!user.canUpdatePreferencesForUserId(request.getUserId())) {
            throw new InsufficientPermissionsToUpdateLecturerPreferences(
                    "Only admin, dziekanat or user for this permission can update them"
            );
        }
        final LecturerPreferences preferenceExists = lecturerPreferencesRepository.findByDayAndUser(
                request.getDay(),
                user
        );
        final LecturerPreferences updatedPreferences = new LecturerPreferences(
                null,
                user,
                request.getStartTime(),
                request.getEndTime(),
                request.getDay()
        );
        if (preferenceExists != null) {
            updatedPreferences.setId(preferenceExists.getId());
            updatedPreferences.setUser(preferenceExists.getUser());
            updatedPreferences.setDay(preferenceExists.getDay());
        }

        return lecturerPreferencesRepository.save(updatedPreferences);
    }

    public LecturerPreferences fetchPreferenceForUserAndDay(Long userId, DayOfWeek day) throws RuntimeException {
        final Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new NoSuchUserException("No username provided");
        }
        final User user = optionalUser.get();
        LecturerPreferences result = lecturerPreferencesRepository.findByDayAndUser(day, user);
        if (result == null) {
            throw new LecturerPreferenceDoesntExist(
                    String.format("Preference for %s %s doesn't exists", user.getUsername(), day)
            );
        }
        return result;
    }
}
