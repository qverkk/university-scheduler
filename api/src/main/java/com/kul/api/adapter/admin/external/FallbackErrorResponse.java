package com.kul.api.adapter.admin.external;

import lombok.Data;

import java.util.List;

@Data
public class FallbackErrorResponse {
    private List<ErrorResponse> errors;
}
