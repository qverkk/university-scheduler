package com.kul.database.controller;

import com.kul.database.model.TokenRequest;
import com.kul.database.model.User;
import com.kul.database.model.UserLogin;
import com.kul.database.model.UserLoginResponse;
import com.kul.database.service.AuthService;
import com.kul.database.service.JpaAuthService;
import org.springframework.web.bind.annotation.*;

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
    public Boolean registerUser(@RequestBody User user) {
        return service.registerUser(user);
    }

    @PostMapping(
            value = "/auth"
    )
    public String authUser(@RequestBody UserLogin user) {
        return service.authenticate(user);
    }

    @PostMapping(
            value = "/login"
    )
    public UserLoginResponse loginWithToken(@RequestBody TokenRequest token) {
        return service.loginWithToken(token.getToken());
    }
}
