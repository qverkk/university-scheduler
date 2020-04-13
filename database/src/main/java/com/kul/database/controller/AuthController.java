package com.kul.database.controller;

import com.kul.database.model.User;
import com.kul.database.model.UserLogin;
import com.kul.database.service.JpaAuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JpaAuthService service;

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

    @GetMapping(
            value = "/login"
    )
    public User loginWithToken(@RequestParam String token) {
        return service.loginWithToken(token);
    }
}