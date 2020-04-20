package com.kul.database.service;

import com.kul.database.constants.AuthorityEnum;
import com.kul.database.constants.JwtUtils;
import com.kul.database.constants.SecurityConstants;
import com.kul.database.model.*;
import com.kul.database.repository.AuthoritiesRepository;
import com.kul.database.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;

@Service("Auth service")
public class JpaAuthService implements AuthService {

    private final UserRepository userRepository;
    private final AuthoritiesRepository authoritiesRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    public JpaAuthService(UserRepository userRepository, AuthoritiesRepository authoritiesRepository) {
        this.userRepository = userRepository;
        this.authoritiesRepository = authoritiesRepository;
    }

    @PostConstruct
    public void init() {
        User user = new User(
                null,
                "admin@admin.com",
                "admin",
                "admin",
                "admin",
                true,
                AuthorityEnum.ADMIN
        );
        registerUser(user);
    }

    @Override
    public String authenticate(UserLoginRequest user) {
        User repositoryUser = userRepository.findByUsername(user.getUsername());
        if (repositoryUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist");
        }
        if (!passwordEncoder.matches(user.getPassword(), repositoryUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Passwords don't match!");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return JwtUtils.generateToken(authentication);
    }

    @Override
    public UserLoginWithTokenResponse loginWithToken(String token) {
        byte[] signinKey = SecurityConstants.JWT_SECRET.getBytes();
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(signinKey)
                    .build()
                    .parseClaimsJws(token.replace("Bearer ", ""));
            String username = claims.getBody().getSubject();

            final User user = userRepository.findByUsername(username);
            return new UserLoginWithTokenResponse(
                    user.getUsername(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getAuthority()
            );
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Unable to authenticate user");
            return null;
        }
    }

    @Override
    public UserRegistrationResponse registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exist");
        }
        user.setId(null);
        if (!user.getUsername().equals("admin@admin.com")) {
            user.setEnabled(false);
        } else {
            user.setEnabled(true);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        authoritiesRepository.save(new Authorities(user.getUsername(), user.getAuthority()));
        return new UserRegistrationResponse(savedUser.getId(), true);
    }
}
