package com.kul.api.adapter.user.authorization;

import com.kul.api.adapter.user.external.AuthEndpointClient;
import com.kul.api.data.Constants;
import com.kul.api.domain.user.authorization.ExistingUser;
import com.kul.api.domain.user.authorization.ExistingUserToken;
import com.kul.api.domain.user.authorization.UserAuthorization;
import com.kul.api.domain.user.authorization.UserInfo;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

public class UserAuthorizationFacade implements UserAuthorization {

    private final AuthEndpointClient authentication = Feign.builder()
            .encoder(new GsonEncoder())
            .decoder(new GsonDecoder())
            .target(AuthEndpointClient.class, Constants.HOST_URL);

    @Override
    public ExistingUserToken authenticate(ExistingUser existingUser) {
        final UserLoginRequest userLoginRequest = new UserLoginRequest(
                existingUser.getUsername(),
                existingUser.getPassword()
        );

        UserLoginResponse token = authentication.generateToken(userLoginRequest);
        return new ExistingUserToken(token.getToken());
    }

    @Override
    public UserInfo loginWithToken(ExistingUserToken userToken) {
        final UserLoginWithTokenRequest request = new UserLoginWithTokenRequest(userToken.getToken());
        final UserLoginWithTokenResponse response = authentication.login(request);
        return new UserInfo(
                response.getUsername(),
                response.getFirstName(),
                response.getLastName(),
                response.getAuthority()
        );
    }
}
