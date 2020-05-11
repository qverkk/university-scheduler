package com.kul.database.lecturerlessons.api.model.areaofstudies;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class AddOrUpdateAreaOfStudiesRequest {
    @JsonProperty
    String area;

    @JsonProperty
    String department;

    @JsonCreator
    public AddOrUpdateAreaOfStudiesRequest(String area, String department) {
        this.area = area;
        this.department = department;
    }
}
