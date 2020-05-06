package com.kul.api.adapter.admin.external;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ErrorResponseException extends RuntimeException {
    private final ErrorResponse errorResponse;

    public ErrorResponseException(Throwable cause, ErrorResponse errorResponse) {
        super(cause);
        this.errorResponse = errorResponse;
    }
}
