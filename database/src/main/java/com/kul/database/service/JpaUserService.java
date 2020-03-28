package com.kul.database.service;

import com.kul.database.constants.JwtUtils;
import com.kul.database.constants.SecurityConstants;
import com.kul.database.model.Authorities;
import com.kul.database.model.Authority;
import com.kul.database.model.User;
import com.kul.database.model.UserLogin;
import com.kul.database.repository.AuthoritiesRepository;
import com.kul.database.repository.AuthorityRepository;
import com.kul.database.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("User service")
public class JpaUserService implements UserService {

    private final UserRepository userRepository;
    private final AuthoritiesRepository authoritiesRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    public JpaUserService(UserRepository userRepository, AuthoritiesRepository authoritiesRepository) {
        this.userRepository = userRepository;
        this.authoritiesRepository = authoritiesRepository;
    }

    @Override
    public String authenticate(UserLogin user) {
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
        return JwtUtils.generateToken(authentication);
    }

    @Override
    public User loginWithToken(String token) {
        byte[] signinKey = SecurityConstants.JWT_SECRET.getBytes();
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(signinKey)
                    .build()
                    .parseClaimsJws(token.replace("Bearer ", ""));
            String username = claims.getBody().getSubject();
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            System.err.println("Unable to authenticate user");
            return null;
        }
    }

    @Override
    public Boolean registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return false;
        }
        user.setId(null);
        user.setEnabled(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (authorityRepository.findByAuthority(user.getAuthority().getAuthority()) == null) {
            Authority authority = authorityRepository.save(new Authority(null, user.getAuthority().getAuthority()));
            user.setAuthority(authority);
        }
        userRepository.save(user);
        authoritiesRepository.save(new Authorities(user.getUsername(), user.getAuthority().getAuthority()));
        return true;
    }
}
