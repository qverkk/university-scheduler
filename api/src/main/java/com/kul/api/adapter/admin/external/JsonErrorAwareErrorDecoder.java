package com.kul.api.adapter.admin.external;

import com.google.gson.Gson;
import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

import java.util.Optional;

class JsonErrorAwareErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder errorDecoder = new Default();
    private final Gson gson = new Gson();

    @Override
    public Exception decode(String methodKey, Response response) {
        Exception exception = errorDecoder.decode(methodKey, response);

        if (exception instanceof RetryableException) {
            return exception;
        } else {
            return parseErrorResponse(exception)
                    .orElse(
                            parseFallbackErrorResponse(exception)
                                    .orElse(exception)
                    );
        }
    }

    private Optional<Exception> parseErrorResponse(Exception exception) {
        return Optional.ofNullable(exception)
                .filter(FeignException.class::isInstance)
                .map(FeignException.class::cast)
                .map(FeignException::contentUTF8)
                .filter(content -> !content.isEmpty())
                .map(content -> gson.fromJson(content, ErrorResponse.class))
                .map(errorResponse -> {
                    if (errorResponse.getCode() == null) {
                        return null;
                    }
                    return new ErrorResponseException(exception, errorResponse);
                });
    }

    private Optional<Exception> parseFallbackErrorResponse(Exception exception) {
        return Optional.ofNullable(exception)
                .filter(FeignException.class::isInstance)
                .map(FeignException.class::cast)
                .map(FeignException::contentUTF8)
                .filter(content -> !content.isEmpty())
                .map(content -> gson.fromJson(content, FallbackErrorResponse.class))
                .map(errorResponse -> new FallbackErrorResponseException(exception, errorResponse));
    }
}
