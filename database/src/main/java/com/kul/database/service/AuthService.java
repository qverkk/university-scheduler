package com.kul.database.service;

import com.kul.database.model.User;
import com.kul.database.model.UserLoginRequest;
import com.kul.database.model.UserLoginWithTokenResponse;
import com.kul.database.model.UserRegistrationResponse;

public interface AuthService {
    String authenticate(UserLoginRequest user);

    UserLoginWithTokenResponse loginWithToken(String token);

    UserRegistrationResponse registerUser(User user);
}
