package com.kul.database.currentsubjects.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.Pattern;

@Value
public class AddOrUpdateCurrentSubjectsRequest {
    String classroomTypeName;
    String classroomName;
    Long lessonId;
    @Pattern(regexp = "^\\d{2}:\\d{2}$")
    String startTime;
    @Pattern(regexp = "^\\d{2}:\\d{2}$")
    String endTime;

    @JsonCreator
    public AddOrUpdateCurrentSubjectsRequest(
            @JsonProperty("classroomTypeName") String classroomTypeName,
            @JsonProperty("classroomName") String classroomName,
            @JsonProperty("lessonId") Long lessonId,
            @JsonProperty("startTime") String startTime,
            @JsonProperty("endTime") String endTime
    ) {
        this.classroomTypeName = classroomTypeName;
        this.classroomName = classroomName;
        this.lessonId = lessonId;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
