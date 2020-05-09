package com.kul.database.lecturerlessons.domain.areaofstudy;

import java.util.List;

public interface AreaOfStudyRepository {
    List<AreaOfStudy> findAll();

    AreaOfStudy addOrUpdateAreaOfStudy(AreaOfStudy areaOfStudy);

    void delete(String area, String department);
}
