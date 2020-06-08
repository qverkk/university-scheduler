package com.kul.api.adapter.admin.classrooms;

import com.kul.api.adapter.admin.classroomtypes.AllClassroomTypesResponse;
import lombok.Value;

import java.util.List;

@Value
public class AllClassroomsResponse {
    Long id;
    String name;
    List<AllClassroomTypesResponse> classroomType;
    Integer classroomSize;
}
