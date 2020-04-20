package com.kul.api.domain.user.registration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisteredInfo {
    private final User user;
    private final RegistrationInfo info;

    public static RegisteredInfo from(User user, RegistrationInfo info) {
        return new RegisteredInfo(user, info);
    }
}
