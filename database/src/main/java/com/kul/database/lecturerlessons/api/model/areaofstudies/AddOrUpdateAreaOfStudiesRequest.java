package com.kul.database.lecturerlessons.api.model.areaofstudies;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddOrUpdateAreaOfStudiesRequest {
    private String area;
    private String department;
}
