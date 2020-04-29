package com.kul.database.lecturerpreferences.adapter.jpa;

import com.kul.database.lecturerpreferences.domain.exceptions.InsufficientPermissionsToUpdateLecturerPreferences;
import com.kul.database.lecturerpreferences.domain.exceptions.LecturerPreferenceAlreadyExists;
import com.kul.database.lecturerpreferences.domain.exceptions.LecturerPreferenceDoesntExist;
import com.kul.database.usermanagement.domain.exceptions.NoSuchUserException;
import com.kul.database.lecturerpreferences.api.AddLecturerPreferenceRequest;
import com.kul.database.lecturerpreferences.api.UpdateLecturerPreferenceRequest;
import com.kul.database.lecturerpreferences.domain.LecturerPreferences;
import com.kul.database.lecturerpreferences.domain.LecturerPreferencesRepository;
import com.kul.database.usermanagement.domain.User;
import com.kul.database.usermanagement.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.Optional;

@Service("Lecturer preferences service")
public class JpaLecturerPreferencesService {

    private final LecturerPreferencesRepository lecturerPreferencesRepository;
    private final UserRepository userRepository;

    public JpaLecturerPreferencesService(LecturerPreferencesRepository lecturerPreferencesRepository, UserRepository userRepository) {
        this.lecturerPreferencesRepository = lecturerPreferencesRepository;
        this.userRepository = userRepository;
    }

    public LecturerPreferences addPreferenceForUser(AddLecturerPreferenceRequest userPreferenceRequest) throws Exception {
        final Optional<User> optionalUser = userRepository.findById(userPreferenceRequest.getUserId());
        if (!optionalUser.isPresent()) {
            throw new NoSuchUserException("No username provided");
        }
        final User user = optionalUser.get();
        if (!user.canUpdatePreferencesForUserId(userPreferenceRequest.getUserId())) {
            throw new InsufficientPermissionsToUpdateLecturerPreferences(
                    "Only admin, dziekanat or user for this permission can update them"
            );
        }
        final LecturerPreferences preferenceExists = lecturerPreferencesRepository.findByDayAndUser(
                userPreferenceRequest.getDay(),
                user
        );
        if (preferenceExists != null) {
            throw new LecturerPreferenceAlreadyExists(
                    String.format("Preference for %s already exists", userPreferenceRequest.getDay())
            );
        }
        final LecturerPreferences preference = new LecturerPreferences(
                null,
                user,
                userPreferenceRequest.getStartTime(),
                userPreferenceRequest.getEndTime(),
                userPreferenceRequest.getDay()
        );
        final LecturerPreferences savedPreference = lecturerPreferencesRepository.save(preference);
        return savedPreference;
    }

    public LecturerPreferences updatePreferenceForUser(UpdateLecturerPreferenceRequest request) throws Exception {
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
        if (preferenceExists == null) {
            throw new LecturerPreferenceDoesntExist(
                    String.format("Preference for %s doesn't exists", request.getDay())
            );
        }
        final LecturerPreferences updatedPreferences = new LecturerPreferences(
                preferenceExists.getId(),
                preferenceExists.getUser(),
                request.getStartTime(),
                request.getEndTime(),
                preferenceExists.getDay()
        );

        final LecturerPreferences savedPreference = lecturerPreferencesRepository.save(updatedPreferences);
        return savedPreference;
    }

    public LecturerPreferences fetchPreferenceForUserAndDay(Long userId, DayOfWeek day) throws Exception {
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
