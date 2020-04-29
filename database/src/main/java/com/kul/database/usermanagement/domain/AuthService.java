package com.kul.database.usermanagement.domain;

import com.kul.database.usermanagement.api.login.UserLoginRequest;
import com.kul.database.usermanagement.api.login.UserLoginWithTokenResponse;
import com.kul.database.usermanagement.api.registration.UserRegistrationResponse;

public interface AuthService {
    String authenticate(UserLoginRequest user);

    UserLoginWithTokenResponse loginWithToken(String token);

    UserRegistrationResponse registerUser(User user);
}
