package com.kul.database.controller;

import com.kul.database.constants.AuthorityEnum;
import com.kul.database.model.AllUsersResponse;
import com.kul.database.model.User;
import com.kul.database.service.JpaUserService;
import com.kul.database.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping(
            value = "/all"
    )
    public List<AllUsersResponse> getAllUsers(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User user = service.getUserByUsername(principal.getName());
        if (user == null || user.getAuthority() != AuthorityEnum.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is unauthorized for this resource");
        }
        return service.getAllUsers().stream().map(u ->
                new AllUsersResponse(
                        u.getId(),
                        u.getUsername(),
                        u.getFirstName(),
                        u.getLastName(),
                        u.getEnabled(),
                        u.getAuthority()
                )
        ).collect(Collectors.toList());
    }
}
