package com.kul.database.controller;

import com.kul.database.model.User;
import com.kul.database.service.JpaUserService;
import com.kul.database.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping(value = "/user")
class UserController {

    private final UserService service;

    public UserController(JpaUserService service) {
        this.service = service;
    }

    @PostMapping(
            value = "/enable/{id}"
    )
    public void enableUser(HttpServletRequest request, @PathVariable Long id) {
        Principal principal = request.getUserPrincipal();
        if (!principal.getName().equals("admin@admin.com")) {
            return;
        }
        service.enableUser(id);
    }

    @DeleteMapping(
            value = "/delete/{id}"
    )
    public void deleteUser(HttpServletRequest request, @PathVariable Long id) {
        Principal principal = request.getUserPrincipal();
        if (!principal.getName().equals("admin@admin.com")) {
            return;
        }
        service.deleteUser(id);
    }

    @GetMapping(
            value = "/enabled/{id}"
    )
    public Boolean isUserEnabled(@PathVariable Long id) {
        return service.isUserEnabled(id);
    }

    @GetMapping(
            value = "/user/{id}"
    )
    public User getUserById(@PathVariable Long id) {
        return service.getUserById(id);
    }
}
