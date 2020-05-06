package com.kul.database.abilities.dtos

import com.kul.database.usermanagement.domain.AuthorityEnum
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

@Builder(builderStrategy = SimpleStrategy, prefix = "with")
class NewUserRequest {
    Long id
    String username
    String password
    String firstName
    String lastName
    Boolean enabled
    AuthorityEnum authority
}

@Builder(builderStrategy = SimpleStrategy, prefix = "with")
class TokenRequest {
    String token;
}

class NewUserRequests {
    static NewUserRequest aNewUserRequest() {
        return new NewUserRequest()
                .withId(1)
                .withFirstName("Janusz")
                .withLastName("Nosacz")
                .withPassword("Januszu123!@#a")
                .withUsername("janusz@nosacz.pl")
                .withEnabled(false)
                .withAuthority(AuthorityEnum.DZIEKANAT)
    }

    static NewUserRequest aDisabledDeaneryUser() {
        return aNewUserRequest()
                .withEnabled(false)
                .withAuthority(AuthorityEnum.DZIEKANAT)
    }
}
