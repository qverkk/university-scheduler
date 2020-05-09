package com.kul.database.lecturerlessons.api.model.areaofstudies;

import com.kul.database.lecturerlessons.domain.areaofstudy.AreaOfStudy;
import lombok.Value;

@Value
public class AddOrUpdateAreaOfStudiesResponse {
    AreaOfStudy areaOfStudy;
}
