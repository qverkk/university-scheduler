package com.kul.window.application.data;

import com.kul.api.domain.user.authorization.ExistingUserToken;
import com.kul.api.domain.user.authorization.UserInfo;

public class UserAndTokenInfo {
    private final ExistingUserToken userToken;
    private final UserInfo userInfo;

    public UserAndTokenInfo(ExistingUserToken userToken, UserInfo userInfo) {
        this.userToken = userToken;
        this.userInfo = userInfo;
    }

    public ExistingUserToken getUserToken() {
        return userToken;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}
