package com.kul.api.adapter.admin.classroomtypes;

import com.kul.api.adapter.admin.external.ErrorResponseException;
import com.kul.api.adapter.admin.management.lecturer.preferences.FailureCause;

public class AddNewClassroomTypeException extends RuntimeException {
    private final FailureCause failureCause;

    public AddNewClassroomTypeException(ErrorResponseException cause, FailureCause failureCause) {
        super(cause);
        this.failureCause = failureCause;
    }
}
