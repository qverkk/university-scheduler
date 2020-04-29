package com.kul.database.service;

import com.kul.database.exceptions.LecturerPreferenceAlreadyExists;
import com.kul.database.exceptions.LecturerPreferenceDoesntExist;
import com.kul.database.exceptions.NoSuchUserException;
import com.kul.database.model.*;
import com.kul.database.repository.LecturerPreferencesRepository;
import com.kul.database.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("Lecturer preferences service")
public class JpaLecturerPreferencesService implements LecturerPreferencesService {

    private final LecturerPreferencesRepository lecturerPreferencesRepository;
    private final UserRepository userRepository;

    public JpaLecturerPreferencesService(LecturerPreferencesRepository lecturerPreferencesRepository, UserRepository userRepository) {
        this.lecturerPreferencesRepository = lecturerPreferencesRepository;
        this.userRepository = userRepository;
    }

    @Override
    public LecturerPreferences addPreferenceForUser(AddLecturerPreferenceRequest userPreferenceRequest) throws NoSuchUserException, LecturerPreferenceAlreadyExists {
        final Optional<User> optionalUser = userRepository.findById(userPreferenceRequest.getUserId());
        if (!optionalUser.isPresent()) {
            throw new NoSuchUserException("No username provided");
        }
        final User user = optionalUser.get();
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

    @Override
    public LecturerPreferences updatePreferenceForUser(UpdateLecturerPreferenceRequest request) throws NoSuchUserException, LecturerPreferenceDoesntExist {
        final Optional<User> optionalUser = userRepository.findById(request.getUserId());
        if (!optionalUser.isPresent()) {
            throw new NoSuchUserException("No username provided");
        }
        final User user = optionalUser.get();
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
}
