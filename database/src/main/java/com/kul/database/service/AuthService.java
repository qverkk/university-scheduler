package com.kul.database.service;

import com.kul.database.model.User;
import com.kul.database.model.UserLogin;
import com.kul.database.model.UserLoginResponse;

public interface AuthService {
    String authenticate(UserLogin user);

    UserLoginResponse loginWithToken(String token);

    Boolean registerUser(User user);
}
