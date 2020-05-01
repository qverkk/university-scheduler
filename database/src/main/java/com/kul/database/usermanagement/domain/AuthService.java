package com.kul.database.usermanagement.domain;

import com.kul.database.infrastructure.helpers.JwtUtils;
import com.kul.database.infrastructure.helpers.SecurityConstants;
import com.kul.database.usermanagement.api.login.UserLoginRequest;
import com.kul.database.usermanagement.api.login.UserLoginWithTokenResponse;
import com.kul.database.usermanagement.api.registration.UserRegistrationResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final AuthoritiesRepository authoritiesRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, AuthoritiesRepository authoritiesRepository) {
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
        if (userRepository.findByUsername("admin@admin.com") == null) {
            registerUser(user);
        }
    }

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
            LOGGER.error("Unable to authenticate user", e);
            return null;
        }
    }

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
