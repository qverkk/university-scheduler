package com.kul.database.classrooms.api.model.classroom;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.List;

@Value
public class AddOrUpdateClassroomRequest {
    @NotNull
    String name;
    @NotNull
    List<String> classroomTypes;
    @NotNull
    Integer classroomSize;

    @JsonCreator
    public AddOrUpdateClassroomRequest(
            @JsonProperty("name") String name,
            @JsonProperty("classroomTypes") List<String> classroomTypes,
            @JsonProperty("classroomSize") Integer classroomSize
    ) {
        this.name = name;
        this.classroomTypes = classroomTypes;
        this.classroomSize = classroomSize;
    }
}
