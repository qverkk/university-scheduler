package com.kul.database.lecturerlessons.api.model.areaofstudies;

import lombok.Value;

@Value
public class AddOrUpdateAreaOfStudiesResponse {
    Long id;
    String area;
    String department;
}
