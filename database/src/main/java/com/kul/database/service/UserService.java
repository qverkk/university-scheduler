package com.kul.database.service;

import com.kul.database.model.User;

public interface UserService {
    String authenticate(User user);
    User loginWithToken(String token);
    Boolean registerUser(User user);
}
