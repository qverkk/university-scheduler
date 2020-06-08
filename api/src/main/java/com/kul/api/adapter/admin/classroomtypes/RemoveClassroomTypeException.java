package com.kul.api.adapter.admin.classroomtypes;

import com.kul.api.adapter.admin.external.ErrorResponseException;
import com.kul.api.adapter.admin.management.lecturer.preferences.FailureCause;

public class RemoveClassroomTypeException extends RuntimeException {
    private final FailureCause failureCause;

    public RemoveClassroomTypeException(ErrorResponseException cause, FailureCause failureCause) {
        super(cause);
        this.failureCause = failureCause;
    }
}
