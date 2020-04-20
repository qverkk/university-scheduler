package com.kul.api.domain.user.authorization;

public interface UserAuthorization {
    ExistingUserToken authenticate(ExistingUser existingUser);
    UserInfo loginWithToken(ExistingUserToken userToken);
}
