package com.kul.api.adapter.admin.lessons;

import lombok.Value;

@Value
public class UpdateOrAddLecturerRequest {
    Long userId;
    String lessonName;
    String area;
    String department;
    String lessonType;
    String semester;
    String year;
}
