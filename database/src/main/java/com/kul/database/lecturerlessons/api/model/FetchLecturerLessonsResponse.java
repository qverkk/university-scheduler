package com.kul.database.lecturerlessons.api.model;

import com.kul.database.lecturerlessons.domain.LecturerLessons;
import lombok.Value;

import java.util.List;

@Value
public class FetchLecturerLessonsResponse {
    List<LecturerLessons> lecturerLessons;
}
