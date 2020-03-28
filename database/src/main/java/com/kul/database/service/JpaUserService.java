package com.kul.database.service;

import com.kul.database.model.User;
import com.kul.database.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service("User service")
public class JpaUserService implements UserService {

    private UserRepository userRepository;

    public JpaUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String authenticate(User user) {
        return null;
    }

    @Override
    public User loginWithToken(String token) {
        return null;
    }

    @Override
    public Boolean registerUser(User user) {
        return null;
    }
}
