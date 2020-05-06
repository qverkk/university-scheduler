package com.kul.database.abilities.dtos

import com.kul.database.abilities.CallUserEndpointAbility
import com.kul.database.usermanagement.domain.AuthorityEnum
import groovy.transform.builder.Builder
import io.restassured.response.ValidatableResponse
import org.junit.After

import java.util.function.Consumer
import java.util.function.Supplier

import static com.kul.database.abilities.dtos.NewUserRequests.aDisabledDeaneryUser;

@Builder(prefix = "with")
class RegisteredUser {
    Long id
}

class UsersFixtures {
    static UserConfiguration janKowalski() {
        new UserConfiguration()
                .whoIsEnabled()
                .whoHasUsername("kowal@gm.com")
                .whoHasAuthority(AuthorityEnum.PROWADZACY)
                .whoHasFirstName("Kowal")
                .whoHasLastName("Adam")
                .whoHasPassword("KowalskiAdam123!@#a")
    }
}

class UserConfiguration {
    private boolean disabled
    private String username
    private String password
    private String firstName
    private String lastName
    private AuthorityEnum authority

    UserConfiguration whoIsDisabled() {
        disabled = true
        this
    }

    UserConfiguration whoIsEnabled() {
        disabled = false
        this
    }

    UserConfiguration whoHasUsername(String username) {
        this.username = username
        this
    }

    UserConfiguration whoHasPassword(String password) {
        this.password = password
        this
    }

    UserConfiguration whoHasFirstName(String firstName) {
        this.firstName = firstName
        this
    }

    UserConfiguration whoHasLastName(String lastName) {
        this.lastName = lastName
        this
    }

    UserConfiguration whoHasAuthority(AuthorityEnum authority) {
        this.authority = authority
        this
    }

    NewUserRequest toNewUserRequest() {
        new NewUserRequest()
            .withUsername(username)
            .withPassword(password)
            .withFirstName(firstName)
            .withLastName(lastName)
            .withAuthority(authority)
            .withEnabled(false)
    }

    String username() {
        username
    }

    String password() {
        password
    }
}

trait UsersManagementAbility implements CallUserEndpointAbility {
    private final List<RegisteredUser> registeredUsers = new LinkedList<>()

    RegisteredUser hasRegisteredUser(Supplier<UserConfiguration> configurer) {
        UserConfiguration userConfiguration = configurer.get();

        def request = registerNewUser(userConfiguration.toNewUserRequest())
        def id = Long.valueOf(request.extract().body().jsonPath().get("newUserAssignedId").toString())

        if (userConfiguration.whoIsEnabled()) {
            enableUser(id)
        }

        return RegisteredUser.builder()
                .withId(id)
                .build()
                .with {
                    registeredUsers.add(it)
                    it
                }
    }

    @After
    void cleanupUsersManagement() {
        registeredUsers.forEach {
            deleteUser(it.id)
        }
    }
}