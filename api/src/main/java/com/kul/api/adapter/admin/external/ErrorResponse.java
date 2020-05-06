package com.kul.api.adapter.admin.external;

import lombok.Data;

@Data
public class ErrorResponse {
    private final String message;
    private final String code;
}
