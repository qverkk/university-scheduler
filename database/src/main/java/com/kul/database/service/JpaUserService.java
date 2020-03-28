package com.kul.database.service;

import com.kul.database.model.Authority;
import com.kul.database.model.User;
import com.kul.database.repository.AuthorityRepository;
import com.kul.database.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("User service")
public class JpaUserService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;

    public JpaUserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
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
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return false;
        }
        user.setId(null);
        user.setEnabled(false);
        user.setUsername(passwordEncoder.encode(user.getPassword()));
        if (authorityRepository.findByAuthority(user.getAuthority().getAuthority()) == null) {
            authorityRepository.save(new Authority(null, user.getAuthority().getAuthority()));
        }
        userRepository.save(user);
        return true;
    }
}
