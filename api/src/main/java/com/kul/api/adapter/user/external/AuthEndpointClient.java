package com.kul.api.adapter.user.external;

import com.kul.api.adapter.user.authorization.UserLoginRequest;
import com.kul.api.adapter.user.authorization.UserLoginResponse;
import com.kul.api.adapter.user.authorization.UserLoginWithTokenResponse;
import com.kul.api.adapter.user.registration.UserRegistrationRequest;
import com.kul.api.adapter.user.authorization.UserLoginWithTokenRequest;
import com.kul.api.adapter.user.registration.UserRegistrationResponse;
import feign.Headers;
import feign.RequestLine;

public interface AuthEndpointClient {
    @RequestLine("POST /auth/auth")
    @Headers("Content-Type: application/json")
    UserLoginResponse generateToken(UserLoginRequest userLoginRequest);

    @RequestLine("POST /auth/register")
    @Headers("Content-Type: application/json")
    UserRegistrationResponse register(UserRegistrationRequest user);

    @RequestLine("POST /auth/login")
    @Headers("Content-Type: application/json")
    UserLoginWithTokenResponse login(UserLoginWithTokenRequest token);
}
