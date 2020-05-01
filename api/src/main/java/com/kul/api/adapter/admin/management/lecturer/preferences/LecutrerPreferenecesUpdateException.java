package com.kul.api.adapter.admin.management.lecturer.preferences;

import com.kul.api.adapter.admin.external.ErrorResponseException;
import lombok.Getter;

@Getter
public class LecutrerPreferenecesUpdateException extends RuntimeException {
    private final FailureCause failureCause;

    public LecutrerPreferenecesUpdateException(ErrorResponseException cause, FailureCause failureCause) {
        super(cause);
        this.failureCause = failureCause;
    }
}
