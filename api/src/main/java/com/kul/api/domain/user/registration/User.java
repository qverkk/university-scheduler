package com.kul.api.domain.user.registration;

import com.kul.api.model.AuthorityEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final Boolean enabled;
    private final AuthorityEnum authority;
    private Long id;

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
