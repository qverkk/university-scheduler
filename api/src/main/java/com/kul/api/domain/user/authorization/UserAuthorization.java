package com.kul.api.domain.user.authorization;

import com.kul.api.adapter.user.authorization.UserAccountDisabledException;
import com.kul.api.adapter.user.authorization.UserLoginAccountDoesntExistException;
import com.kul.api.adapter.user.authorization.UserLoginWrongPasswordException;

public interface UserAuthorization {
    ExistingUserToken authenticate(ExistingUser existingUser) throws UserAccountDisabledException, UserLoginWrongPasswordException, UserLoginAccountDoesntExistException;
    UserInfo loginWithToken(ExistingUserToken userToken);
}
