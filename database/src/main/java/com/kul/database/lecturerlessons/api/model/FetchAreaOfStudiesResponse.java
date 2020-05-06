package com.kul.database.lecturerlessons.api.model;

import com.kul.database.lecturerlessons.domain.AreaOfStudy;
import lombok.Value;

import java.util.List;

@Value
public class FetchAreaOfStudiesResponse {
    List<AreaOfStudy> areaOfStudies;
}
