package com.kul.api.domain.admin.areaofstudies;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AreaOfStudiesManagement {
    private final AreaOfStudiesRepository areaOfStudiesRepository;

    public void addNewAreaOfStudies(AreaOfStudies newAreaOfStudies) {
        areaOfStudiesRepository.addNewAreaOfStudies(newAreaOfStudies);
    }

    public List<AreaOfStudies> getAllAreaOfStudies() {
        return areaOfStudiesRepository.getAllAreaOfStudies();
    }

    public void removeAreaOfStudies(String area, String department) {
        areaOfStudiesRepository.removeAreaOfStudies(area, department);
    }
}
