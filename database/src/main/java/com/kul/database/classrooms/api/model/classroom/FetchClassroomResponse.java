package com.kul.database.classrooms.api.model.classroom;

import com.kul.database.classrooms.api.model.classroomtype.FetchClassroomTypesResponse;
import lombok.Value;

import java.util.List;

@Value
public class FetchClassroomResponse {
    Long id;
    String name;
    List<FetchClassroomTypesResponse> classroomType;
    Integer classroomSize;
}
