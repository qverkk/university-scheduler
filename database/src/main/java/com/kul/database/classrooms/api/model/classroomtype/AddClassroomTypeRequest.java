package com.kul.database.classrooms.api.model.classroomtype;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class AddClassroomTypeRequest {
    String name;

    @JsonCreator
    public AddClassroomTypeRequest(
            @JsonProperty("name") String name
    ) {
        this.name = name;
    }
}
