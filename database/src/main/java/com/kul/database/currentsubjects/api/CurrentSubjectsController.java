package com.kul.database.currentsubjects.api;

import com.kul.database.currentsubjects.api.model.*;
import com.kul.database.currentsubjects.domain.CurrentSubjects;
import com.kul.database.currentsubjects.domain.CurrentSubjectsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/currentsubjects")
public class CurrentSubjectsController {

    private final CurrentSubjectsService service;

    public CurrentSubjectsController(CurrentSubjectsService service) {
        this.service = service;
    }

    @GetMapping(
            value = "/fetch/all"
    )
    public List<FetchCurrentSubjectsResponse> fetchAllCurrentSubjects() {
        return service.fetchAllCurrentSubjects().stream()
                .map(FetchCurrentSubjectsMapper::toResponse)
                .collect(Collectors.toList());
    }

    @PutMapping(
            value = "/add"
    )
    public AddOrUpdateCurrentSubjectsResponse addOrUpdateCurrentSubjects(@RequestBody AddOrUpdateCurrentSubjectsRequest request) {
        final CurrentSubjects currentSubjects = service.addOrUpdate(
                AddOrUpdateCurrentSubjectsMapper.toDomain(request)
        );
        return AddOrUpdateCurrentSubjectsMapper.fromDomain(currentSubjects);
    }
}
