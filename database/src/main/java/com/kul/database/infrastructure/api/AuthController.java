package com.kul.database.infrastructure.api;

import com.kul.database.usermanagement.domain.AuthService;
import com.kul.database.usermanagement.adapter.jpa.JpaAuthService;
import com.kul.database.usermanagement.api.login.TokenRequest;
import com.kul.database.usermanagement.api.login.UserLoginRequest;
import com.kul.database.usermanagement.api.login.UserLoginResponse;
import com.kul.database.usermanagement.api.login.UserLoginWithTokenResponse;
import com.kul.database.usermanagement.api.registration.UserRegistrationResponse;
import com.kul.database.usermanagement.domain.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
class AuthController {

    private final AuthService service;

    public AuthController(JpaAuthService service) {
        this.service = service;
    }

    @PostMapping(
            value = "/register"
    )
    public UserRegistrationResponse registerUser(@RequestBody User user) {
        return service.registerUser(user);
    }

    @PostMapping(
            value = "/auth"
    )
    public UserLoginResponse authUser(@RequestBody UserLoginRequest user) {
        return new UserLoginResponse(service.authenticate(user));
    }

    @PostMapping(
            value = "/login"
    )
    public UserLoginWithTokenResponse loginWithToken(@RequestBody TokenRequest token) {
        return service.loginWithToken(token.getToken());
    }
}
