package com.kul.database.controller;

import com.kul.database.exceptions.InsufficientPersmissionsToDeleteUsersException;
import com.kul.database.exceptions.InsufficientPersmissionsToEnableUsersException;
import com.kul.database.exceptions.InsufficientPersmissionsToGetAllUserData;
import com.kul.database.exceptions.NoSuchUserException;
import com.kul.database.model.AllUsersResponse;
import com.kul.database.model.User;
import com.kul.database.service.JpaUserService;
import com.kul.database.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
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
    public void enableUser(Principal principal, @PathVariable Long id) throws NoSuchUserException, InsufficientPersmissionsToEnableUsersException {
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is unauthorized for this resource");
        }
        service.enableUser(id, principal.getName());
    }

    @PostMapping(
            value = "/disable/{id}"
    )
    public void disableUser(Principal principal, @PathVariable Long id) throws NoSuchUserException, InsufficientPersmissionsToEnableUsersException {
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is unauthorized for this resource");
        }
        service.disableUser(id, principal.getName());
    }

    @DeleteMapping(
            value = "/delete/{id}"
    )
    public void deleteUser(Principal principal, @PathVariable Long id) throws NoSuchUserException, InsufficientPersmissionsToDeleteUsersException {
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is unauthorized for this resource");
        }
        service.deleteUser(id, principal.getName());
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
    public List<AllUsersResponse> getAllUsers(Principal principal) throws NoSuchUserException, InsufficientPersmissionsToGetAllUserData {
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is unauthorized for this resource");
        }
        return service.getAllUsers(principal.getName()).stream().map(u ->
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
