package com.kul.database.service;

import com.kul.database.constants.AuthorityEnum;
import com.kul.database.constants.JwtUtils;
import com.kul.database.constants.SecurityConstants;
import com.kul.database.model.Authorities;
import com.kul.database.model.User;
import com.kul.database.model.UserLogin;
import com.kul.database.repository.AuthoritiesRepository;
import com.kul.database.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service("User service")
public class JpaUserService implements UserService {

    private final UserRepository userRepository;
    private final AuthoritiesRepository authoritiesRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    public JpaUserService(UserRepository userRepository, AuthoritiesRepository authoritiesRepository) {
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
    public String authenticate(UserLogin user) {
        User repositoryUser = userRepository.findByUsername(user.getUsername());
        if (repositoryUser == null) {
            return null;
        }
        if (!passwordEncoder.matches(user.getPassword(), repositoryUser.getPassword())) {
            return null;
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return JwtUtils.generateToken(authentication);
        } catch (DisabledException e) {
            System.out.println("TODO: Add catching of exceptions in tests");
            e.printStackTrace();
            return "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
            e.printStackTrace();
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
        if (!user.getUsername().equals("admin@admin.com")) {
            user.setEnabled(false);
        } else {
            user.setEnabled(true);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        authoritiesRepository.save(new Authorities(user.getUsername(), user.getAuthority()));
        return true;
    }
}
