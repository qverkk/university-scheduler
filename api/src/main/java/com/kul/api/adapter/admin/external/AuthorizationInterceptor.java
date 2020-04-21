package com.kul.api.adapter.admin.external;

import com.kul.api.domain.user.authorization.ExistingUserToken;
import feign.RequestInterceptor;
import feign.RequestTemplate;

public class AuthorizationInterceptor implements RequestInterceptor {

    private final ExistingUserToken userToken;

    public AuthorizationInterceptor(ExistingUserToken userToken) {
        this.userToken = userToken;
    }

    @Override
    public void apply(RequestTemplate template) {
        template.header("Authorization", String.format("%s %s", "Bearer", userToken.getToken()));
    }
}
