package com.kul.api.domain.admin.areaofstudies;

import java.util.List;

public interface AreaOfStudiesRepository {
    void addNewAreaOfStudies(AreaOfStudies newAreaOfStudies);

    List<AreaOfStudies> getAllAreaOfStudies();

    void removeAreaOfStudies(String area, String department);
}
