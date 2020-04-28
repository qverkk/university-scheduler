package com.kul.database.service;

import com.kul.database.exceptions.LecturerPreferenceAlreadyExists;
import com.kul.database.exceptions.NoSuchUserException;
import com.kul.database.model.LecturerPreferenceRequest;
import com.kul.database.model.LecturerPreferences;
import com.kul.database.model.User;
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
    public void addPreferenceForUser(LecturerPreferenceRequest userPreferenceRequest) throws NoSuchUserException, LecturerPreferenceAlreadyExists {
        final Optional<User> optionalUser = userRepository.findById(userPreferenceRequest.getUserId());
        if (!optionalUser.isPresent()) {
            throw new NoSuchUserException("No username provided");
        }
        final User user = optionalUser.get();
        final Boolean preferenceExists = lecturerPreferencesRepository.findByDayAndUser(
                userPreferenceRequest.getDay(),
                user
        );
        if (preferenceExists) {
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
        lecturerPreferencesRepository.save(preference);
    }
}
