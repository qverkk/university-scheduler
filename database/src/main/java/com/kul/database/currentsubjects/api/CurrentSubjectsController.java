package com.kul.database.currentsubjects.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/currentsubjects")
public class CurrentSubjectsController {

    @GetMapping(
            value = "/fetch/all"
    )
    public void fetchAllCurrentSubjects() {

    }
}
