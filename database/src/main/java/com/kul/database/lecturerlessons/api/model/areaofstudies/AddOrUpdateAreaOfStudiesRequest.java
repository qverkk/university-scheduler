package com.kul.database.lecturerlessons.api.model.areaofstudies;

import lombok.Value;

@Value
public class AddOrUpdateAreaOfStudiesRequest {
    String area;
    String department;
}
