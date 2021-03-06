package com.kul.api.adapter.user.authorization;

import com.kul.api.adapter.user.external.AuthEndpointClient;
import com.kul.api.domain.user.authorization.ExistingUser;
import com.kul.api.domain.user.authorization.ExistingUserToken;
import com.kul.api.domain.user.authorization.UserAuthorization;
import com.kul.api.domain.user.authorization.UserInfo;
import feign.FeignException;

public class UserAuthorizationFacade implements UserAuthorization {

    private final AuthEndpointClient authentication;

    public UserAuthorizationFacade(AuthEndpointClient authentication) {
        this.authentication = authentication;
    }

    @Override
    public ExistingUserToken authenticate(ExistingUser existingUser) throws UserAccountDisabledException, UserLoginWrongPasswordException, UserLoginAccountDoesntExistException {
        try {
            final UserLoginRequest userLoginRequest = new UserLoginRequest(
                    existingUser.getUsername(),
                    existingUser.getPassword()
            );

            UserLoginResponse token = authentication.generateToken(userLoginRequest);
            return new ExistingUserToken(token.getToken());
        } catch (FeignException.NotFound error) {
            throw new UserLoginAccountDoesntExistException();
        } catch (FeignException.Unauthorized unauthorized) {
            throw new UserLoginWrongPasswordException();
        } catch (FeignException.Forbidden forbidden) {
            throw new UserAccountDisabledException();
        }
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
