package com.kul.api.adapter.user.authorization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserLoginWithTokenRequest {
    private String token;
}
