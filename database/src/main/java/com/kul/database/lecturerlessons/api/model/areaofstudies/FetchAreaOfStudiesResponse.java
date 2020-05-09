package com.kul.database.lecturerlessons.api.model.areaofstudies;

import com.kul.database.lecturerlessons.domain.areaofstudy.AreaOfStudy;
import lombok.Value;

import java.util.List;

@Value
public class FetchAreaOfStudiesResponse {
    List<AreaOfStudy> areaOfStudies;
}
