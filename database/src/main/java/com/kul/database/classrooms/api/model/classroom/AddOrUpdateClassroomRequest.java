package com.kul.database.classrooms.api.model.classroom;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class AddOrUpdateClassroomRequest {
    String name;
    String classroomType;

    @JsonCreator
    public AddOrUpdateClassroomRequest(
            @JsonProperty("name") String name,
            @JsonProperty("classroomType") String classroomType
    ) {
        this.name = name;
        this.classroomType = classroomType;
    }
}
