package com.kul.api.adapter.admin.external;

import com.google.gson.Gson;
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
                    .orElse(exception);
        }
    }

    private Optional<Exception> parseErrorResponse(Exception exception) {
        return Optional.ofNullable(exception)
                .map(Exception::getMessage)
                .filter(message -> !message.isEmpty())
                .map(message -> gson.fromJson(message.replaceAll(".*]: ", ""), ErrorResponse[].class))
                .map(errorResponse -> new ErrorResponseException(exception, errorResponse));
    }
}
