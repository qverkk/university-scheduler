package com.kul.database.usermanagement.domain;

import com.kul.database.lecturerlessons.domain.LecturerLessonsRepository;
import com.kul.database.lecturerpreferences.domain.LecturerPreferencesRepository;
import com.kul.database.usermanagement.domain.exceptions.InsufficientPersmissionsToDeleteUsersException;
import com.kul.database.usermanagement.domain.exceptions.InsufficientPersmissionsToEnableUsersException;
import com.kul.database.usermanagement.domain.exceptions.InsufficientPersmissionsToGetAllUserData;
import com.kul.database.usermanagement.domain.exceptions.NoSuchUserException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final LecturerPreferencesRepository lecturerPreferencesRepository;
    private final LecturerLessonsRepository lecturerLessonsRepository;

    public UserService(UserRepository userRepository, LecturerPreferencesRepository lecturerPreferencesRepository, LecturerLessonsRepository lecturerLessonsRepository) {
        this.userRepository = userRepository;
        this.lecturerPreferencesRepository = lecturerPreferencesRepository;
        this.lecturerLessonsRepository = lecturerLessonsRepository;
    }

    public void enableUser(Long id, String username) {
        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchUserException(username));
        if (!user.canEnableUsers()) {
            throw new InsufficientPersmissionsToEnableUsersException(user);
        }
        userRepository.findById(id).ifPresent(u -> {
            u.setEnabled(true);
            userRepository.save(u);
        });
    }

    public void disableUser(Long id, String username) {
        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchUserException(username));
        if (!user.canDisableUsers()) {
            throw new InsufficientPersmissionsToEnableUsersException(user);
        }
        userRepository.findById(id).ifPresent(u -> {
            u.setEnabled(false);
            userRepository.save(u);
        });
    }

    public void deleteUser(Long id, String username) {
        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchUserException(username));
        if (!user.canDeleteUsers()) {
            throw new InsufficientPersmissionsToDeleteUsersException(user);
        }
        lecturerLessonsRepository.deleteAllByUserId(id);
        lecturerPreferencesRepository.deleteAllByUserId(id);
        userRepository.deleteById(id);
    }

    public Boolean isUserEnabled(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.map(User::getEnabled).orElse(null);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElse(null);
    }

    public List<User> getAllUsers(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchUserException(username));
        if (!user.hasAccessToAllUserData()) {
            throw new InsufficientPersmissionsToGetAllUserData(username);
        }
        return userRepository.findAll();
    }
}
