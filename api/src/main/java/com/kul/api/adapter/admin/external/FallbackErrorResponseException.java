package com.kul.api.adapter.admin.external;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FallbackErrorResponseException extends RuntimeException {
    private final FallbackErrorResponse errorResponse;

    public FallbackErrorResponseException(Exception exception, FallbackErrorResponse errorResponse) {
        super(exception);
        this.errorResponse = errorResponse;
    }
}
