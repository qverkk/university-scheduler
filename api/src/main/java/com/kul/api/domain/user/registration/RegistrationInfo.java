package com.kul.api.domain.user.registration;

import lombok.Getter;

import java.util.Objects;

@Getter
public class RegistrationInfo {
    private final Long newUserAssignedId;
    private final Boolean success;

    public RegistrationInfo(Long newUserAssignedId, Boolean success) {
        this.newUserAssignedId = Objects.requireNonNull(newUserAssignedId, "User id cannot be null");
        this.success = success;
    }

//    public static RegistrationInfo from(User user) {
//        return new RegistrationInfo(user.getId());
//    }
}
