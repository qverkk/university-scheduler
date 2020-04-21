package com.kul.database.service;

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
    public void enableUser(Long id) {
        userRepository.findById(id).ifPresent(u -> {
            u.setEnabled(true);
            userRepository.save(u);
        });
    }

    @Override
    public void disableUser(Long id) {
        userRepository.findById(id).ifPresent(u -> {
            u.setEnabled(false);
            userRepository.save(u);
        });
    }

    @Override
    public void deleteUser(Long id) {
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
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
