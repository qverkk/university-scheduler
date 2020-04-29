package com.kul.database.usermanagement.adapter.jpa;

import com.kul.database.usermanagement.domain.exceptions.InsufficientPersmissionsToDeleteUsersException;
import com.kul.database.usermanagement.domain.exceptions.InsufficientPersmissionsToEnableUsersException;
import com.kul.database.usermanagement.domain.exceptions.InsufficientPersmissionsToGetAllUserData;
import com.kul.database.usermanagement.domain.exceptions.NoSuchUserException;
import com.kul.database.usermanagement.domain.User;
import com.kul.database.usermanagement.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("User service")
public class JpaUserService {

    private final UserRepository userRepository;

    public JpaUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void enableUser(Long id, String username) throws NoSuchUserException, InsufficientPersmissionsToEnableUsersException {
        final User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NoSuchUserException(username);
        } else if (!user.canEnableUsers()) {
            throw new InsufficientPersmissionsToEnableUsersException(user);
        }
        userRepository.findById(id).ifPresent(u -> {
            u.setEnabled(true);
            userRepository.save(u);
        });
    }

    public void disableUser(Long id, String username) throws NoSuchUserException, InsufficientPersmissionsToEnableUsersException {
        final User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NoSuchUserException(username);
        } else if (!user.canDisableUsers()) {
            throw new InsufficientPersmissionsToEnableUsersException(user);
        }
        userRepository.findById(id).ifPresent(u -> {
            u.setEnabled(false);
            userRepository.save(u);
        });
    }

    public void deleteUser(Long id, String username) throws NoSuchUserException, InsufficientPersmissionsToDeleteUsersException {
        final User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NoSuchUserException(username);
        } else if (!user.canDeleteUsers()) {
            throw new InsufficientPersmissionsToDeleteUsersException(user);
        }
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
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsers(String username) throws NoSuchUserException, InsufficientPersmissionsToGetAllUserData {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NoSuchUserException(username);
        } else if (!user.hasAccessToAllUserData()) {
            throw new InsufficientPersmissionsToGetAllUserData(username);
        }
        return userRepository.findAll();
    }
}
