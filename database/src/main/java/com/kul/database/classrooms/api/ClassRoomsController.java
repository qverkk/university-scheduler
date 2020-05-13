package com.kul.database.classrooms.api;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/classroom")
public class ClassRoomsController {

    @PutMapping(
            value = "/add"
    )
    public void addOrUpdateClassRoom() {

    }
}
