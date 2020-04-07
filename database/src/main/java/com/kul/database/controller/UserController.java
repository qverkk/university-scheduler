package com.kul.database.controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @PostMapping(
            value = "/enable/{id}"
    )
    public Boolean enableUser(HttpServletRequest request, @PathVariable String id) {
        Principal principal = request.getUserPrincipal();

        return false;
    }
}
