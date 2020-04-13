package com.kul.api.http.requests;

import com.kul.api.model.UserLogin;
import feign.Headers;
import feign.RequestLine;

public interface AuthRequest {
    @RequestLine("POST /auth/auth")
    @Headers("Content-Type: application/json")
    String generateToken(UserLogin userLogin);
}
