package com.kul.api.domain.user.registration;

import com.kul.api.model.AuthorityEnum;
import lombok.Value;

@Value
public class User {
    Long id;
    String username;
    String password;
    String firstName;
    String lastName;
    Boolean enabled;
    AuthorityEnum authority;

    public User(Long id, String username, String password, String firstName, String lastName, Boolean enabled, AuthorityEnum authority) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = enabled;
        this.authority = authority;
    }

    public User(String username, String password, String firstName, String lastName, AuthorityEnum authority) {
        this(null, username, password, firstName, lastName, false, authority);
    }

    public User setId(Long id) {
        return new User(
                id,
                this.username,
                this.password,
                this.firstName,
                this.lastName,
                this.enabled,
                this.authority
        );
    }

    public static User from(NewUser newUser) {
        return new User(
                newUser.getUsername(),
                newUser.getPassword(),
                newUser.getFirstName(),
                newUser.getLastName(),
                newUser.getAuthority()
        );
    }
}
