package com.kul.database.lecturerlessons.adapter.areaofstudy;

import com.kul.database.lecturerlessons.domain.areaofstudy.AreaOfStudy;

public class AreaOfStudyEntityMapper {
    public static AreaOfStudyEntity fromDomain(AreaOfStudy areaOfStudy) {
        return new AreaOfStudyEntity(
                areaOfStudy.getId(),
                areaOfStudy.getDepartment(),
                areaOfStudy.getArea()
        );
    }

    public static AreaOfStudy toDomain(AreaOfStudyEntity areaOfStudyEntity) {
        return new AreaOfStudy(
                areaOfStudyEntity.getId(),
                areaOfStudyEntity.getDepartment(),
                areaOfStudyEntity.getArea()
        );
    }
}
