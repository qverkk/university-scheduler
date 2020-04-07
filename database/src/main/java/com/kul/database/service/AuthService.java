package com.kul.database.service;

import com.kul.database.model.User;
import com.kul.database.model.UserLogin;

public interface AuthService {
    String authenticate(UserLogin user);

    User loginWithToken(String token);

    Boolean registerUser(User user);
}
