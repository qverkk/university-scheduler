package com.kul.api.adapter.user.registration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRegistrationResponse {
    private final Long newUserAssignedId;
    private final Boolean success;
}
