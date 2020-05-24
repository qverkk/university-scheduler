package com.kul.database.classrooms.api.model.classroom;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class AddOrUpdateClassroomRequest {
    String name;
    List<String> classroomTypes;
    Integer classroomSize;

    @JsonCreator
    public AddOrUpdateClassroomRequest(
            @JsonProperty("name") String name,
            @JsonProperty("classroomType") List<String> classroomTypes,
            @JsonProperty("classroomSize") Integer classroomSize
    ) {
        this.name = name;
        this.classroomTypes = classroomTypes;
        this.classroomSize = classroomSize;
    }
}
