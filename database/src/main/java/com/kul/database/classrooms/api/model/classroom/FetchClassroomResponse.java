package com.kul.database.classrooms.api.model.classroom;

import com.kul.database.classrooms.api.model.classroomtype.FetchClassroomTypesResponse;
import lombok.Value;

@Value
public class FetchClassroomResponse {
    Long id;
    String name;
    FetchClassroomTypesResponse fetchClassroomTypesResponse;
}
