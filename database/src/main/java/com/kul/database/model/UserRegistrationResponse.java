package com.kul.database.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRegistrationResponse {
    private final Long newUserAssignedId;
    private final Boolean success;
}
