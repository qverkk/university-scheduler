package com.kul.database.service;

import com.kul.database.model.User;
import com.kul.database.repository.UserRepository;
import org.springframework.stereotype.Service;

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
            System.out.println("Is present, enabling:");
            u.setEnabled(true);
            userRepository.save(u);
            System.out.println("Enabled: " + u.getEnabled());
        });
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        System.out.println("Deleted");
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
}
