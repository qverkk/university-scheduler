package com.kul.database.lecturerlessons.domain.areaofstudy;

import lombok.Getter;

@Getter
public class AreaOfStudy {
    private final Long id;
    private final String department;
    private final String area;

    public AreaOfStudy(Long id, String department, String area) {
        this.id = id;
        this.department = department;
        this.area = area;
    }

    public AreaOfStudy(String department, String area) {
        this(null, department, area);
    }

    public static AreaOfStudy newForDepartmentAndArea(String department, String area) {
        return new AreaOfStudy(department, area);
    }
}
