package com.kul.api.domain.user.registration;

import lombok.Getter;

import java.util.Objects;

@Getter
public class RegistrationInfo {
    private final Long newUserAssignedId;

    public RegistrationInfo(Long newUserAssignedId) {
        this.newUserAssignedId = Objects.requireNonNull(newUserAssignedId, "User id cannot be null");
    }
}
