package com.kul.database.infrastructure.api;

import com.kul.database.usermanagement.domain.UserService;
import com.kul.database.usermanagement.api.AllUsersResponse;
import com.kul.database.usermanagement.domain.User;
import com.kul.database.usermanagement.domain.exceptions.InsufficientPersmissionsToDeleteUsersException;
import com.kul.database.usermanagement.domain.exceptions.InsufficientPersmissionsToEnableUsersException;
import com.kul.database.usermanagement.domain.exceptions.InsufficientPersmissionsToGetAllUserData;
import com.kul.database.usermanagement.domain.exceptions.NoSuchUserException;
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

    public UserController(UserService service) {
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
    public List<AllUsersResponse> getAllUsers(Principal principal) throws RuntimeException {
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
