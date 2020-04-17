package com.kul.database.abilities.dtos

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

@Builder(builderStrategy = SimpleStrategy, prefix = "with")
class UserLoginRequest {
    String username
    String password
}

class UserLoginRequests {
    static UserLoginRequest aUser(String username, String password) {
        return new UserLoginRequest()
                .withUsername(username)
                .withPassword(password)
    }
}
