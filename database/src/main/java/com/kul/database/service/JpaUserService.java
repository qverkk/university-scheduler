package com.kul.database.service;

import com.kul.database.constants.JwtUtils;
import com.kul.database.model.Authority;
import com.kul.database.model.User;
import com.kul.database.repository.AuthorityRepository;
import com.kul.database.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("User service")
public class JpaUserService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final AuthenticationManager authenticationManager;

    public JpaUserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String authenticate(User user) {
        User repositoryUser = userRepository.findByUsername(user.getUsername());
        if (repositoryUser == null) {
            return null;
        }
        if (!passwordEncoder.matches(user.getPassword(), repositoryUser.getPassword())) {
            return null;
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = JwtUtils.generateToken(authentication);
        return token;
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
