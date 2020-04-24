package com.kul.database.service;

import com.kul.database.exceptions.InsufficientPersmissionsToDeleteUsersException;
import com.kul.database.exceptions.InsufficientPersmissionsToEnableUsersException;
import com.kul.database.exceptions.InsufficientPersmissionsToGetAllUserData;
import com.kul.database.exceptions.NoSuchUserException;
import com.kul.database.model.User;
import com.kul.database.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("User service")
public class JpaUserService implements UserService {

    private final UserRepository userRepository;

    public JpaUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
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

    @Override
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

    @Override
    public void deleteUser(Long id, String username) throws NoSuchUserException, InsufficientPersmissionsToDeleteUsersException {
        final User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NoSuchUserException(username);
        } else if (!user.canDeleteUsers()) {
            throw new InsufficientPersmissionsToDeleteUsersException(user);
        }
        userRepository.deleteById(id);
    }

    @Override
    public Boolean isUserEnabled(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.map(User::getEnabled).orElse(null);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers(String username) throws NoSuchUserException, InsufficientPersmissionsToGetAllUserData {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NoSuchUserException(username);
        } else if (user.hasAccessToAllUserData()) {
            throw new InsufficientPersmissionsToGetAllUserData(username);
        }
        return userRepository.findAll();
    }
}
