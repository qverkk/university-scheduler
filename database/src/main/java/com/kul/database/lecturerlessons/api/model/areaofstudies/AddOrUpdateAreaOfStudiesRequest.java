package com.kul.database.lecturerlessons.api.model.areaofstudies;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class AddOrUpdateAreaOfStudiesRequest {
    String area;
    String department;

    @JsonCreator
    public AddOrUpdateAreaOfStudiesRequest(
            @JsonProperty("area") String area,
            @JsonProperty("department") String department
    ) {
        this.area = area;
        this.department = department;
    }
}
