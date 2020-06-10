package com.kul.api.adapter.admin.areaofstudies;

import lombok.Value;

import java.util.List;

@Value
public class FetchAllAreaOfStudiesResponse {
    List<FetchAreaOfStudiesResponse> areaOfStudies;
}
