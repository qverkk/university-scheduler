package com.kul.api.http.requests;

import com.kul.api.model.TokenRequest;
import com.kul.api.model.UserLogin;
import com.kul.api.model.UserLoginResponse;
import com.kul.api.model.UserRegistration;
import feign.Headers;
import feign.RequestLine;

public interface AuthRequest {
    @RequestLine("POST /auth/auth")
    @Headers("Content-Type: application/json")
    String generateToken(UserLogin userLogin);

    @RequestLine("POST /auth/register")
    @Headers("Content-Type: application/json")
    String register(UserRegistration user);

    @RequestLine("POST /auth/login")
    @Headers("Content-Type: application/json")
    UserLoginResponse login(TokenRequest token);
}
