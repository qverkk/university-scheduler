package com.kul.database.lecturerlessons.domain;

import lombok.Getter;

@Getter
public enum Semester {
    SUMMER("Letni"),
    WINTER("Zimowy");

    private final String semesterName;

    Semester(String semesterName) {
        this.semesterName = semesterName;
    }
}
