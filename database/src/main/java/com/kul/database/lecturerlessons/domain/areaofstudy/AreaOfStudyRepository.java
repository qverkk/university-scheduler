package com.kul.database.lecturerlessons.domain.areaofstudy;

import java.util.List;
import java.util.Optional;

public interface AreaOfStudyRepository {
    List<AreaOfStudy> findAll();

    AreaOfStudy addOrUpdateAreaOfStudy(AreaOfStudy areaOfStudy);

    void delete(String area, String department);

    Optional<AreaOfStudy> findByAreaAndDepartment(String area, String department);
}
